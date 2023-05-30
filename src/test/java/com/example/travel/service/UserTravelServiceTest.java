package com.example.travel.service;

import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;
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
        UserSaveDTO dto = UserSaveDTO.builder()
                .userId("지민테스트")
                .userEmail("admin@naver.com")
                .userPassword("user1234")
                .userName("관리자")
                .userBirthday("930430")
                .userGender("여")
                .userPhone("010-1111-1111")
                .address("테스트")
                .addressPostcode("234")
                .addressDetail("t")
                .addressExtra("T")
                .build();
        // 저장확인
        System.out.println(userService.userSave(dto));
    }

    @Test
    @DisplayName("2. user 정보 가져오기")
    public void test2(){
        UserSaveResultDTO user = userService.userGetNo(1L);
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
        UserSaveDTO dto = UserSaveDTO.builder()
                .userNo(1L)
                .userEmail("admin@gmail.com")
                .userPassword("admin")
                .userName("수정 관리자")
                .userPhone("010-2333-2333")
                .address("경기도")
                .build();

        UserSaveResultDTO result = userService.userModitfy(dto);
        System.out.println(result);

    }



}