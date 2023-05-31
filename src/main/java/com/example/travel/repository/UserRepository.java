package com.example.travel.repository;

import com.example.travel.domain.UserTravel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTravel,Long> {
    UserTravel getUserByUserNo(Long no);
    UserTravel getUserByUserId(String id);


    @Query(value = "select m from UserTravel m where m.userSocial = :social and m.userId = :id",
    nativeQuery = false)
    Optional<UserTravel> getUserByUserIdAndUserSocial(@Param(value = "id") String id, @Param(value = "social") boolean social); //로그인 시 필요.
}
