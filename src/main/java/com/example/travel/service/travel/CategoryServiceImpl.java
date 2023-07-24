package com.example.travel.service.travel;

import com.example.travel.domain.*;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import com.example.travel.dto.travel.LikeCategoryDTO;
import com.example.travel.dto.travel.TagDTO;
import com.example.travel.repository.travel.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;
    final HashtagRepository hashtagRepository;
    final TagRepository tagRepository;
    final ItemRepository itemRepository;
    final LikeCategoryRepository likeRepository;


    @Override
    public List<CategoryDTO> getCategoryTemList(Long no) {
        log.info("임시 저장된 내용 전달");
        List<CategoryDTO> result = new ArrayList<>();
        try{
            List<Category> userTravelNo = categoryRepository.getCategoryTemList(no);
            if (userTravelNo.isEmpty()){
                log.info("없으면 null");
                return result;
            }
            log.info("있으면 리스트 전달");
            log.info(userTravelNo);
            result = userTravelNo.stream().map(item -> categoryEntityToDto(item)).collect(Collectors.toList());            return result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public Page<CategoryDTO> getCategoryList(Integer page, String order) {
        log.info("전체 카테고리 리스트");
        PageRequest pageRequest;

        pageRequest = PageRequest.of(page - 1, 6, Sort.by(order).ascending());
        Page<Category> result = categoryRepository.findByCategoryOpen(true, pageRequest);
        Page<CategoryDTO> categoryDTOS = convertPage(result);

        return categoryDTOS;
    }
    @Override
    public Page<CategoryDTO> getCategoryMYPage(Long no, Integer page, String order) {
        log.info("내 카테고리 리스트 전달");
        log.info("page : {}",page);
        PageRequest pageRequest;

        pageRequest = PageRequest.of(page - 1, 6, Sort.by(order).ascending());
        Page<Category> result = categoryRepository.findByUserTravelNoAndCategorySave(no,true, pageRequest);
        Page<CategoryDTO> categoryDTOS = convertPage(result);

        return categoryDTOS;
    }



    @Override
    @Transactional
    public Page<CategoryDTO> getCategoryInvitedMYPage(
            String name,
            Integer page, String order) {

        log.info("내 카테고리 초대 리스트 전달 --------");
        log.info("내 아이디 : {}",name);

        // 태그 이름을 통해 카테고리 아이디 가져오기
        List<Long> categoryNoList = new ArrayList<>();
        List<Tag> tagList = tagRepository.findTagByName(name);
        if (!tagList.isEmpty()){
            for (int i=0;i<tagList.size();i++){
                Long id = tagList.get(i).getId();
                System.out.println(id);
                Long categoryId = hashtagRepository.findByTag(tagList.get(i)).getCategoryId();
                categoryNoList.add(categoryId);
            }
        }


        // 카테고리 리스트 생성
        List<Category> categoryList = new ArrayList<>();


        for (int i = 0; i<categoryNoList.size();i++){
            Category one = categoryRepository.getOne(categoryNoList.get(i));
            if (one.isCategorySave() == true) categoryList.add(one);
        }

        PageRequest pageRequest;
        pageRequest = PageRequest.of(page - 1, 6, Sort.by(order).ascending());

        Page<Category> Cpage = new PageImpl<>(categoryList, PageRequest.of(page- 1, 6,Sort.by(order).ascending()), categoryNoList.size());

        Page<CategoryDTO> categoryDTOS = convertPage(Cpage);


        return categoryDTOS;
    }


    Page<CategoryDTO> convertPage(Page<Category> categoryPage) {

        List<CategoryDTO> categoryDTOList = categoryPage.getContent()
                .stream()
                .map(i->categoryEntityToDto(i)
                ) // Assuming CategoryDTO constructor takes a Category object
                .collect(Collectors.toList());

        for (int i=0;i<categoryDTOList.size();i++){
            Long categoryNo = categoryDTOList.get(i).getCategoryNo();
            Hashtag byCategoryId = hashtagRepository.findByCategoryId(categoryNo);
            List<Tag> tags = byCategoryId.getTag();
            String tagInfo="";
            String[] tagInfoList = new String[tags.size()];
            for(int j=0;j<tags.size();j++){
                log.info("tag : {}",tags.get(j).getName());
                tagInfo += "#" + tags.get(j).getName() + " ";
                tagInfoList[j] = tags.get(j).getName();
            }
            categoryDTOList.get(i).setTagList(tagInfoList);
            categoryDTOList.get(i).setTags(tagInfo);
        }

        return new PageImpl<>(categoryDTOList, PageRequest.of(categoryPage.getNumber(), categoryPage.getSize()), categoryPage.getTotalElements());
    }


    @Transactional
    @Override
    public CategoryDTO categorySave(CategoryDTO categoryDTO) {
        log.info("카테고리 임시저장 로직-----------");
        Long userTravelNo = categoryDTO.getUserTravelNo();
        Long categoryNo = categoryDTO.getCategoryNo();
        log.info("카테고리 no : {}",categoryNo);
        log.info(userTravelNo);

        DayInfoDTO dayInfoDTO = categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        int day = dayInfoDTO.getDay();

        categoryDTO.setDateTxt(day);

        // 1. 카테고리저장
        // 2. 태그 저장
        // 3. 해쉬태그 저장

        List<CategoryDTO> categoryTemList = getCategoryTemList(categoryDTO.getUserTravelNo());
        int size = categoryTemList.size();
        if (size > 4){
            log.info("카테고리 임시 저장 리스트 5개 이상 있으면");
            return null;
        }

        //2. 태그저장
        String tags = categoryDTO.getTags();
        log.info("tags : {}" ,tags);
        Category result;
        if (categoryNo == null){
            categoryDTO.setCategorySave(false); //초기 저장 시 임시저장
            Category category = categoryDtoToEntity(categoryDTO);
            result = categoryRepository.save(category);  // 1. 카테고리저장

            log.info("처음 저장 시 --------------");
            hashTagSave(tags,result);
        }else {

            Category one = categoryRepository.getOne(categoryNo);
            CategoryDTO categoryDTORe = categoryEntityToDto(one);
            // 기존 카테고리에 수정내용 추가
            categoryDTORe.setCategoryName(categoryDTO.getCategoryName());
            categoryDTORe.setCategoryArea(categoryDTO.getCategoryArea());
            categoryDTORe.setCategoryAreaDetails(categoryDTO.getCategoryAreaDetails());
            categoryDTORe.setDateEnd(categoryDTO.getDateEnd());
            categoryDTORe.setDateStart(categoryDTO.getDateStart());
            categoryDTORe.setCategorySave(categoryDTORe.isCategorySave());
            categoryDTORe.setCategoryOpen(categoryDTO.isCategoryOpen());
            Category category = categoryDtoToEntity(categoryDTORe);

            result = categoryRepository.save(category);  // 1. 카테고리저장
            log.info("수정일때 --------------");
            Hashtag byCategoryId = hashtagRepository.findByCategoryId(categoryNo);
            if (byCategoryId != null){
                Long hashId = byCategoryId.getHashId();
                hashTagDelete(hashId);
            }
            hashTagSave(tags,result);
        }

        CategoryDTO dto = categoryEntityToDto(result);
        return dto;
    }



    @Override
    public CategoryDTO getCategory(long no) {
        log.info("getCategory--카테고리 가져오기");
        Optional<Category> categorys = categoryRepository.findById(no);
        if (categorys.isEmpty()){
            return null;
        }
        Category category = categorys.get();
        CategoryDTO categoryDTO = categoryEntityToDto(category);




        Hashtag byCategoryId = hashtagRepository.findByCategoryId(no);
        log.info("1");
        if (byCategoryId != null) {
            List<Tag> tags = byCategoryId.getTag();
            String tagInfo = "";
            String[] tagInfoList = new String[tags.size()];
            for (int j = 0; j < tags.size(); j++) {
                log.info("tag : {}", tags.get(j).getName());
                tagInfo += "#" + tags.get(j).getName() + " ";
                tagInfoList[j] = tags.get(j).getName();
            }
            categoryDTO.setTagList(tagInfoList);
            categoryDTO.setTags(tagInfo);
        }

        return categoryDTO;
    }

    @Transactional
    @Override
    public boolean categoryDelete(long no) { // no = 카테고리 번호
        log.info("카테고리 제거 로직 ---------------");


        Optional<Category> categorys = categoryRepository.findById(no);
        Category category = categorys.get();

        //아이템 제거
        List<Item> itemByCategory = itemRepository.findItemByCategory(category);
        if(!itemByCategory.isEmpty()){
            log.info("item 이 있으면");
            for(int i=0;i<itemByCategory.size();i++){
                log.info("item : {}",itemByCategory.get(i));
                itemRepository.delete(itemByCategory.get(i));
            }
        }

        // 1. 태그 제거
        // 2. 해쉬태그 제거
        // 3. 해쉬태그 중간 맵핑 제거
        // 4. 카테고리 제거
        hashTagDelete(no);
        categoryRepository.delete(category); //4. 카테고리제거

        log.info("카테고리가 없으면 false 반환");
        return true;
    }

    public void hashTagSave(String tags,Category result){
        List<TagDTO> itemList = new ArrayList<>();
        List<Tag> tagList =new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String[] arr;
            itemList = mapper.readValue(tags, new TypeReference<List<TagDTO>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < itemList.size(); i++) {
            String name = itemList.get(i).getValue();
            Tag tag = Tag.builder().name(name).build();
            tagList.add(tag);
            tagRepository.save(tag); // 2. 태그 저장
        }

        Hashtag hashtag = Hashtag.builder()
                .categoryId(result.getCategoryNo())
                .tag(tagList)
                .tag(tagList)
                .build();
        hashtagRepository.save(hashtag); // 3. 해쉬태그 저장
    }
    public void hashTagDelete(Long no){
        Optional<Hashtag> hashtag = hashtagRepository.findById(no);
        if (hashtag.isPresent()){
            log.info("해시태그 .isPresent()");
            log.info("카테고리 연관 해시태그, 태그 제거 로직 -------------");
            Hashtag hashtagResult = hashtag.get();

            List<Tag> tag = hashtagResult.getTag();

            if (!tag.isEmpty()){ // 리스트가 존재하면 제거
                log.info("리스트 존재");
                for(int i=0;i<tag.size();i++){
                    Tag tagItem = tag.get(i);
                    int result = hashtagRepository.deleteByHashIdAndTag(tagItem.getId()); // 해쉬태그안에 tag
                    System.out.println(result);
                    tagRepository.delete(tagItem); // 1. tag 제거
                }
                hashtagRepository.deleteByHashIdAndTag(hashtagResult.getHashId()); //중간 태그 제거
                hashtagRepository.delete(hashtagResult); // 2. 해쉬태그 제거

            } else {
                hashtagRepository.deleteByHashIdAndTag(hashtagResult.getHashId()); //중간 태그 제거
                hashtagRepository.delete(hashtagResult); // 3. 해쉬태그 제거
            }
        } // 해쉬태그, 태그 제거 end
    }

    @Override
    public DayInfoDTO categoryDays(String start, String end) {
        // DayInfoDTO 전달 값 객체
        // 객체 안에 들어가야 하는 것
            // D-DAY 몇일인지
            //  처음 날짜 부터 마지막 날짜까지 년,월,일 [];


        log.info("D-day 계산");

        // 문자열
        start = start + " 00:00:00.000";
        end = end + " 00:00:00.000";

        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);


        log.info("------------------");
        Period period = Period.between(LocalDate.from(startDate), LocalDate.from(endDate)); // 비교
        int days = period.getDays();
        days = days + 1;
        log.info("days : {}",days);

        DayInfoDTO result = DayInfoDTO.builder().day(days).build();

        String[] daysInfo = new String[days];
        for(int i=0; i < days;i++){
            LocalDateTime days1 = startDate.plusDays(i);
            int year = days1.getYear();
            int month = days1.getMonthValue();
            int day = days1.getDayOfMonth();
            DayOfWeek dayOfWeek =days1.getDayOfWeek();
            String displayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
            String dayResult = year +"-"+ month +"-"+ day + " " + displayName;
            daysInfo[i] = dayResult;
            System.out.println(daysInfo[i]);
        }

        result.setDayInfo(daysInfo);

        return result;
    }

    @Override
    public boolean categoryTotalSave(long no) {
        Category category = categoryRepository.getOne(no);
        CategoryDTO categoryDTO = categoryEntityToDto(category);
        categoryDTO.setCategorySave(true);
        int categoryTotalPrice = 0;

        //총 여행 금액 저장
        List<Item> itemByCategory = itemRepository.findItemByCategory(category);
        for(int i=0; i<itemByCategory.size();i++){
            int itemAccount = itemByCategory.get(i).getItemAccount();
            if (itemAccount >= 0) {
                categoryTotalPrice += itemAccount;
            }
        }

        categoryDTO.setCategoryTotalPrice(categoryTotalPrice);

        Category result = categoryDtoToEntity(categoryDTO);
        categoryRepository.save(result);

        return true;
    }

    @Transactional
    @Override
    public boolean categoryLike(long categoryNo, long userNo) {

        log.info("카테고리 좋아요 기능 --------------------");

        LikeCategory likeSave = LikeCategory.builder().categoryId(categoryNo).userId(userNo).build();

        Optional<LikeCategory> likes = likeRepository.findByCategoryIdAndUserId(categoryNo, userNo);
        if (likes.isPresent()){
            log.info("좋아요 제거");
            LikeCategory likeCategory = likes.get();
            likeRepository.deleteById(likeCategory.getId());
            return false;
        }else {
            log.info("좋아요");
            LikeCategory save = likeRepository.save(likeSave);
            return true;
        }
    }

    @Override
    public List<LikeCategoryDTO> categoryLikeList(long userNo) {
        List<LikeCategory> likeList = likeRepository.findByUserId(userNo);
        List<LikeCategoryDTO> none = new ArrayList<>();
        if(! likeList.isEmpty()){
            List<LikeCategoryDTO> result = likeList.stream().map(i -> LikeCategoryDTO.builder().categoryId(i.getCategoryId()).userId(i.getUserId()).build()).collect(Collectors.toList());
            return result;
        }

        return none;
    }


}
