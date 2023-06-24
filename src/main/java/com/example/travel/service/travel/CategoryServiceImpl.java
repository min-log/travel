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

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;
    @Override
    public boolean categorySave(CategoryDTO categoryDTO) {
        // 카테고리저장
        // 해쉬태그 저장
        // 태그 저장

        Category category = dtoToEntity(categoryDTO);
        categoryRepository.save(category);


        return false;
    }

    @Override
    public int categoryDays(String start, String end) {
        log.info("D-day 계산");

        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

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
