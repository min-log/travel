package com.example.travel.repository.travel;

import com.example.travel.domain.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking,Long> {
    Optional<Ranking> findRankingByTitleLike(@Param(value = "key") String keyword);
    @Query(value = "select r from Ranking r where r.searchNum >= :num")
    Page<Ranking> findAllSearchNum(Pageable pageable,@Param(value = "num") int searchNum);
}
