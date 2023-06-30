package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findItemByCategory(Category category);

    @Query(value = "select i from Item i left join Category c on i.category=c where i.category.categoryNo=:cNo and i.itemDay=:iDay order by i.itemNumber" ,nativeQuery = false)
    List<Item> findItemList(@Param("iDay") int itemNo,@Param("cNo") Long categoryNo);
}
