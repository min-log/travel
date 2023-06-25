package com.example.travel.repository.travel;

import com.example.travel.domain.Hashtag;
import com.example.travel.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    @Modifying
    @Query(value = "delete from hashtag_tag h where h.tag_tag_id = :id",nativeQuery = true)
    int deleteByHashIdAndTag(@Param(value="id") Long id);

}
