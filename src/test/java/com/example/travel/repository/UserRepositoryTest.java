package com.example.travel.repository;

import com.example.travel.domain.UserTravel;
import groovy.util.logging.Log4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("1. 회원확인")
    public void test(){
        String userId = "test222";

        Optional<UserTravel> userByUserIdAndUserSocial = userRepository.getUserByUserIdAndUserSocial(userId, false);
        if (userByUserIdAndUserSocial.isPresent()){
            UserTravel userTravel = userByUserIdAndUserSocial.get();
            System.out.println("userTravel : " + userTravel );
        }else{
            System.out.println("없음");
        }

    }



}
