package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import com.example.travel.dto.travel.CategoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("저장")
    public void test(){
        Category build = Category.builder()
                .userTravelNo(1L)
                .categoryName("서울2")
                .categorySave(true)
                .categoryArea("서울")
                .build();

        categoryRepository.save(build);
    }

    @Test
    public void getList1(){
        List<Category> userTravelNoOrderByCreatedAt = categoryRepository.getCategoryTemList(1L);
        for(int i =0;i<userTravelNoOrderByCreatedAt.size();i++){

            String categoryName = userTravelNoOrderByCreatedAt.get(i).getCategoryName();
            System.out.println(categoryName);

        }
    }
}