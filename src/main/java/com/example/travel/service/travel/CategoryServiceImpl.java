package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Hashtag;
import com.example.travel.domain.Item;
import com.example.travel.domain.Tag;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import com.example.travel.dto.travel.TagDTO;
import com.example.travel.repository.travel.CategoryRepository;
import com.example.travel.repository.travel.HashtagRepository;
import com.example.travel.repository.travel.ItemRepository;
import com.example.travel.repository.travel.TagRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;
    final HashtagRepository hashtagRepository;
    final TagRepository tagRepository;
    final ItemRepository itemRepository;


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


    @Override
    public CategoryDTO categorySave(CategoryDTO categoryDTO) {
        log.info("카테고리 임시저장 로직-----------");
        Long userTravelNo = categoryDTO.getUserTravelNo();
        log.info(userTravelNo);
        // 1. 카테고리저장
        // 2. 태그 저장
        // 3. 해쉬태그 저장

        List<CategoryDTO> categoryTemList = getCategoryTemList(categoryDTO.getUserTravelNo());
        int size = categoryTemList.size();
        if (size > 4){
            log.info("카테고리 임시 저장 리스트 5개 이상 있으면");
            return null;
        }
        categoryDTO.setCategorySave(false); //초기 저장 시 임시저장
        Category category = categoryDtoToEntity(categoryDTO);
        log.info("저장된 category : {}",category);

        Category result = categoryRepository.save(category);  // 1. 카테고리저장






        //2. 태그저장
        String tags = categoryDTO.getTags();
        if (tags!=null) {
//            log.info("tag가 있으면 저장");
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
                    .build();
            hashtagRepository.save(hashtag); // 3. 해쉬태그 저장
        }

        CategoryDTO dto = categoryEntityToDto(result);
        return dto;
    }

    @Override
    public CategoryDTO getCategory(long no) {
        Optional<Category> categorys = categoryRepository.findById(no);
        if (categorys.isEmpty()){
            return null;
        }
        Category category = categorys.get();
        CategoryDTO categoryDTO = categoryEntityToDto(category);
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
                hashtagRepository.delete(hashtagResult); // 2. 해쉬태그 제거
            } else {
                hashtagRepository.delete(hashtagResult); // 3. 해쉬태그 제거
            }
        } // 해쉬태그, 태그 제거 end

        categoryRepository.delete(category); //4. 카테고리제거

        log.info("카테고리가 없으면 false 반환");
        return true;
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
        Category result = categoryDtoToEntity(categoryDTO);
        categoryRepository.save(result);

        return true;
    }
}
