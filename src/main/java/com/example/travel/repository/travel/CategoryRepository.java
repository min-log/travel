package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query(value = "select c from Category c where c.userTravelNo=:no and c.categorySave = false ORDER BY c.createdAt")
    List<Category> getCategoryTemList(@Param(value = "no") Long no);


    @Query(value = "select c from Category c where c.categorySave = true and c.categoryOpen = :open " +
            "and (c.categoryArea like :key or c.categoryAreaDetails like :key or c.categoryName like :key)")
    Page<Category> findCategorySearch(@Param("open") boolean open,
                                      @Param("key") String keyword ,Pageable pageable);

    @Query(value = "select c from Category c where c.categorySave = true and c.categoryOpen = :open and c.boardImg != null")
    Page<Category> findCategoryImgSearch(@Param("open") boolean open, Pageable pageable);

    @Query(value = "select c from Category c where c.categorySave = true and c.categoryOpen = :open")
    Page<Category> findCategoryPopularitySearch(@Param("open") boolean open, Pageable pageable);


    Page<Category> findByUserTravelNoAndCategorySave(Long no,boolean save, Pageable pageable);







}
