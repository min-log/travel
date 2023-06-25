package com.example.travel.service.travel;

import com.example.travel.dto.travel.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {
    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("회원 카테고리생성")
    public  void test(){
        CategoryDTO build = CategoryDTO.builder()
                .userTravelNo(1L)
                .categoryName("서울2")
                .categorySave(true)
                .dateStart("2201-03-03")
                .dateEnd("2201-03-03")
                .categoryArea("서울")
                .build();
        CategoryDTO categoryDTO = categoryService.categorySave(build);
        System.out.println(categoryDTO);
    }

    @Test
    @DisplayName("회원 게시판 임시저장")
    public void test1(){
        List<CategoryDTO> categoryTemList = categoryService.getCategoryTemList(1L);
        if (categoryTemList.isEmpty()){
            categoryTemList.forEach(i->{
                System.out.println(i.getCategoryName());
            });
        }
    }

    @Test
    @DisplayName("회원 게시판 ㅋㅏ테고리 제거")
    public void test2(){
        boolean b = categoryService.categoryDelete(1L);
        System.out.println(b);
    }

}