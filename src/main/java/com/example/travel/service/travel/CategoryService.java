package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Tag;
import com.example.travel.dto.travel.CategoryDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public interface CategoryService {
    boolean categorySave(CategoryDTO categoryDTO);
    int categoryDays(String start, String end);





    default Category dtoToEntity(CategoryDTO categoryDTO){
        // 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        // 문자열
        String dateS = categoryDTO.getDateStart();
        String dateE = categoryDTO.getDateEnd();
        // 문자열 -> Date
        //LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

        Category result = Category.builder()
                .categoryNo(categoryDTO.getUserTravelNo())
                .categoryName(categoryDTO.getCategoryName())
                .dateStart(LocalDateTime.parse(dateS, formatter))
                .dateEnd(LocalDateTime.parse(dateE, formatter))
                .categoryArea(categoryDTO.getCategoryArea())
                .categoryAreaDetails(categoryDTO.getCategoryAreaDetails())
                .categoryOpen(categoryDTO.isCategoryOpen())
                .build();
        return result;
    }


}
