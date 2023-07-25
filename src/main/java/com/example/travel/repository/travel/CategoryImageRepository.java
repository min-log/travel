package com.example.travel.repository.travel;

import com.example.travel.domain.CategoryImage;
import com.example.travel.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage,Long> {

}
