
package com.example.travel.repository.travel;

import com.example.travel.domain.CategoryBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryBoardRepository extends JpaRepository<CategoryBoard,Long> {

    @Query(value = "select b from CategoryBoard b where b.boardCategoryNo=:no and b.boardItemDay=:dayNo" )
    Optional<CategoryBoard> getGategoryBoardVer(@Param(value = "no") Long no,@Param(value = "dayNo") int dayNo);

    List<CategoryBoard> getCategoryBoardByBoardCategoryNo(Long CategoryNo);



}
