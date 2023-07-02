package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Tag;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public interface CategoryService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    List<CategoryDTO> getCategoryTemList(Long no); //임시 저장 리스트 

    CategoryDTO categorySave(CategoryDTO categoryDTO); // 카테고리 임시 저장
    CategoryDTO getCategory(long no); // 일치하는 카테고리 불러오기

    boolean categoryDelete(long no);
    DayInfoDTO categoryDays(String start, String end);

    boolean categoryTotalSave(long no); //실제 저장



    default CategoryDTO categoryEntityToDto(Category category){
        // 문자열
        String startDate = category.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = category.getDateEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CategoryDTO result = CategoryDTO.builder()
                .userTravelNo(category.getUserTravelNo())
                .categoryNo(category.getCategoryNo())
                .categoryName(category.getCategoryName())
                .dateStart(startDate)
                .dateEnd(endDate)
                .categoryArea(category.getCategoryArea())
                .categoryAreaDetails(category.getCategoryAreaDetails())
                .categorySave(category.isCategorySave())
                .categoryOpen(category.isCategoryOpen())
                .build();

        return result;
    }
    default Category categoryDtoToEntity(CategoryDTO categoryDTO){
        // 문자열
        String start = categoryDTO.getDateStart() + " 00:00:00.000";
        String end = categoryDTO.getDateEnd() + " 00:00:00.000";
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        Category result = Category.builder()
                .userTravelNo(categoryDTO.getUserTravelNo())
                .categoryNo(categoryDTO.getCategoryNo())
                .categoryName(categoryDTO.getCategoryName())
                .dateStart(startDate)
                .dateEnd(endDate)
                .categoryArea(categoryDTO.getCategoryArea())
                .categoryAreaDetails(categoryDTO.getCategoryAreaDetails())
                .categorySave(categoryDTO.isCategorySave())
                .categoryOpen(categoryDTO.isCategoryOpen())
                .build();
        return result;
    }

    
}
