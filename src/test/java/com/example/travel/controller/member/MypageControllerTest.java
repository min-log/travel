package com.example.travel.controller.member;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MypageControllerTest {
    @Autowired
    PasswordEncoder passwordEncoder; // DI
    @Autowired
    UserService userService;

    @Test
    @DisplayName("비밀번호 일치 확인")
    @Transactional
    public void passwordCK(){
        String id = "dizel93";
        String pw = "qwer1234";
        String pwE = passwordEncoder.encode(pw);

        UserDTO build = UserDTO.builder().userId(id).build();
        UserTravel userTravel = userService.userInfo(build);

        String pwEncode = userTravel.getPassword();
        System.out.println(pwEncode);

        boolean matches = passwordEncoder.matches(pw,pwEncode);
        System.out.println(matches);

    }

    @Test
    @DisplayName("회원정보 수정")
    @Transactional
    public void userModify(){


    }





}