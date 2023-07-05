package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query(value = "select c from Category c where c.userTravelNo=:no and c.categorySave = false ORDER BY c.createdAt")
    List<Category> getCategoryTemList(@Param(value = "no") Long no);

    @Query(value = "select c from Category c where c.userTravelNo=:no and c.categorySave = true ORDER BY c.createdAt")
    List<Category> getCategoryList(@Param(value = "no") Long no);

    Page<Category> findByUserTravelNoAndCategorySave(Long no,boolean save, Pageable pageable);



}
