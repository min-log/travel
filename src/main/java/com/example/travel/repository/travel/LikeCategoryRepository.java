package com.example.travel.repository.travel;

import com.example.travel.domain.LikeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCategoryRepository extends JpaRepository<LikeCategory,Long> {
    Optional<LikeCategory> findByCategoryIdAndUserId(long categoryNo,long userNo);
    List<LikeCategory> findByUserId(long userNo);

}
