package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Hashtag;
import com.example.travel.domain.Tag;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.repository.travel.CategoryRepository;
import com.example.travel.repository.travel.HashtagRepository;
import com.example.travel.repository.travel.TagRepository;
import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;
    final HashtagRepository hashtagRepository;
    final TagRepository tagRepository;


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

    @Transactional
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
        if (!categoryDTO.getTags().isEmpty()){
            log.info("tag가 있으면 저장");
            List<String> tags = categoryDTO.getTags();
            List<Tag> tagList = tags.stream().map(i -> Tag.builder().name(i).build()).collect(Collectors.toList());

            for(int i=0; i < tagList.size(); i++){
                tagRepository.save(tagList.get(i)); // 2. 태그 저장
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

    @Transactional
    @Override
    public boolean categoryDelete(Long no) {
        log.info("카테고리 제거 로직 ---------------");

        Optional<Category> categorys = categoryRepository.findById(no);
        Category category = categorys.get();
        categoryRepository.delete(category); //3. 카테고리제거

        // 1. 태그 제거
        // 2. 해쉬태그 제거
        // 3. 카테고리 제거
        log.info(no); // 카테고리 번호
        Optional<Hashtag> hashtag = hashtagRepository.findById(no);
        log.info("hashtag : {}",hashtag);

        if (hashtag.isPresent()){
            log.info("카테고리 연관 해시태그, 태그 제거 로직 -------------");
            Hashtag hashtagResult = hashtag.get();
            Long categoryId = hashtagResult.getCategoryId(); // 카테고리 번호

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
            return true;
        } // 해쉬태그, 태그 제거 end


        log.info("카테고리가 없으면 false 반환");
        return false;
    }

    @Override
    public int categoryDays(String start, String end) {
        log.info("D-day 계산");

        // 문자열
        start = start + " 00:00:00.000";
        end = end + " 00:00:00.000";

        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        log.info("------------------");
        Period period = Period.between(LocalDate.from(startDate), LocalDate.from(endDate)); // 비교
        int days = period.getDays();
        log.info(days);
        days = days + 1;

        log.info("days : {}",days);
        return days;
    }
}
