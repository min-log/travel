package com.example.travel.service;

import com.example.travel.domain.UserRole;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTravelServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("1. user 회원가입")
    public void test1(){
        UserDTO dto = UserDTO.builder()
                .userId("user3")
                .userEmail("admin@naver.com")
                .password("user1234")
                .userName("관리자")
                .userBirthday("930430")
                .userGender("여")
                .userPhone("010-1111-1111")
                .address("테스트")
                .addressPostcode("234")
                .addressDetail("t")
                .addressExtra("T")
                .userSocial(false)
                .build();
        dto.roleAdd(UserRole.USER);
        // 저장확인
        System.out.println(userService.userSave(dto));
    }

    @Test
    @DisplayName("2. user 정보 가져오기")
    public void test2(){
        UserDTO user = userService.userGetNo(1L);
        System.out.println(user);
    }

    @Test
    @DisplayName("getUserByUserId")
    public void testuserId(){
        int result = userService.userGetId("지민테스트");
        System.out.println(result);
    }


    @Test
    @DisplayName("3. user 정보 수정")
    public void test3(){
        UserDTO dto = UserDTO.builder()
                .userNo(1L)
                .userEmail("admin@gmail.com")
                .password("admin")
                .userName("수정 관리자")
                .userPhone("010-2333-2333")
                .address("경기도")
                .build();

        UserDTO result = userService.userModitfy(dto);
        System.out.println(result);

    }



}