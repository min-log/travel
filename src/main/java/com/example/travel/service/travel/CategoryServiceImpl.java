package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.repository.travel.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
        // 카테고리저장
        // 해쉬태그 저장
        // 태그 저장
        List<CategoryDTO> categoryTemList = getCategoryTemList(categoryDTO.getUserTravelNo());
        int size = categoryTemList.size();
        if (size > 4){
            log.info("5개 이상 리스트가 있으면");
            return null;
        }
        categoryDTO.setCategorySave(false); //초기 임시저장
        Category category = categoryDtoToEntity(categoryDTO);
        log.info("저장된 category : {}",category);

        Category result = categoryRepository.save(category);
        CategoryDTO dto = categoryEntityToDto(result);

        return dto;
    }

    @Override
    public boolean categoryDelete(Long no) {
        Optional<Category> categorys = categoryRepository.findById(no);
        if (!categorys.isPresent()){
            return false;
        }
        Category category = categorys.get();
        categoryRepository.delete(category);

        return true;
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
