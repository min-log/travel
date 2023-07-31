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

    Page<Category> findByCategoryOpen(boolean open, Pageable pageable);


//    @Query(value = "set @rownum :=2; " +
//            "select  @rownum := @rownum+1 AS no, c.* " +
//            "from (select b.category_name , b.date_start from category b " +
//            "      where category_open=:open" +
//            "          and category_name like '%제주%' " +
//            "         or category_area like '%제주%' " +
//            "         or category_area_details like '%제주%') c " +
//            "where (@rownum:=0)=0 " +
//            "order by date_start desc " +
//            "limit 3,2 "
//            , nativeQuery = true)
//    Page<Category> searchCategory(boolean open,
//                                 String key,
//                                 int size ,
//                                int page);


    Page<Category> findByUserTravelNoAndCategorySave(Long no,boolean save, Pageable pageable);





}
