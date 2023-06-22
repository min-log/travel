package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.repository.travel.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
}
