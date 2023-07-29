
package com.example.travel.repository.travel;

import com.example.travel.domain.CategoryBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryBoardRepository extends JpaRepository<CategoryBoard,Long> {

    @Query(value = "select b from CategoryBoard b where b.boardCategoryNo=:no and b.boardItemDay=:dayNo" )
    Optional<CategoryBoard> getGategoryBoardVer(@Param(value = "no") Long no,@Param(value = "dayNo") int dayNo);
}
