package com.example.travel.repository;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserTravel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends ImageRepository {
    @Query("select u from UserImage u where u.originFileName = :file")
    Optional<UserImage> findByOriginFileName(@Param(value = "file") String originFileName);
}
