package com.example.travel.repository.travel;

import com.example.travel.domain.CategoryBoard;
import com.example.travel.domain.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage,Long> {

    Optional<CategoryImage> getCategoryImageByBoardNo(CategoryBoard categoryBoard);

}
