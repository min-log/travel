
package com.example.travel.repository.travel;

import com.example.travel.domain.CategoryBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryBoardRepository extends JpaRepository<CategoryBoard,Long> {

    Optional<CategoryBoard> getCategoryBoardByBoardCategoryNo(Long no);
}
