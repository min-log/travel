package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Tag;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import com.example.travel.dto.travel.LikeCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public interface CategoryService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    List<CategoryDTO> getCategoryTemList(Long no); //임시 저장 리스트
    Page<CategoryDTO> getCategoryList(Integer page, String order, String keyword);


    Page<CategoryDTO> getCategoryMYPage(Long no,Integer page ,String order,Integer size);

    Page<CategoryDTO> getCategoryInvitedMYPage(String name, Integer page, String order);

    Page<CategoryDTO> getCategoryLikeMYPage(Long no, Integer page, String order,Integer size);



    CategoryDTO categorySave(CategoryDTO categoryDTO); // 카테고리 임시 저장
    CategoryDTO categoryUpdate(CategoryDTO categoryDTO); // 카테고리 수정
    CategoryDTO getCategory(long no); // 일치하는 카테고리 불러오기

    boolean categoryDelete(long no);

    DayInfoDTO categoryDays(String start, String end);

    boolean categoryTotalSave(long no); //실제 저장

    boolean categoryLike(long categoryNo,long userNo);

    List<LikeCategoryDTO> categoryLikeList(long userNo);






    default CategoryDTO categoryEntityToDto(Category category){
        // 문자열
        String startDate = category.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = category.getDateEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CategoryDTO result = CategoryDTO.builder()
                .userTravelNo(category.getUserTravelNo())
                .categoryWriter(category.getCategoryWriter())
                .categoryNo(category.getCategoryNo())
                .categoryName(category.getCategoryName())
                .dateStart(startDate)
                .dateEnd(endDate)
                .dateTxt(category.getDateTxt())
                .categoryArea(category.getCategoryArea())
                .categoryAreaDetails(category.getCategoryAreaDetails())
                .categorySave(category.isCategorySave())
                .categoryOpen(category.isCategoryOpen())
                .createdAt(category.getCreatedAt())
                .categoryTotalPrice(category.getCategoryTotalPrice())
                .viewNum(category.getViewNum())
                .boardExistence(category.getBoardExistence())
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
                .categoryWriter(categoryDTO.getCategoryWriter())
                .categoryNo(categoryDTO.getCategoryNo())
                .categoryName(categoryDTO.getCategoryName())
                .dateStart(startDate)
                .dateEnd(endDate)
                .dateTxt(categoryDTO.getDateTxt())
                .categoryArea(categoryDTO.getCategoryArea())
                .categoryAreaDetails(categoryDTO.getCategoryAreaDetails())
                .categorySave(categoryDTO.isCategorySave())
                .categoryOpen(categoryDTO.isCategoryOpen())
                .categoryTotalPrice(categoryDTO.getCategoryTotalPrice())
                .viewNum(categoryDTO.getViewNum())
                .boardExistence(categoryDTO.getBoardExistence())
                .build();
        return result;
    }

    
}
