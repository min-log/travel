package com.example.travel.repository;

import com.example.travel.domain.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTravel,Long> {
    UserTravel getUserByUserNo(Long no);
    UserTravel getUserByUserId(String id);
}
