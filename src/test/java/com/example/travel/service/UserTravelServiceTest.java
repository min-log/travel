package com.example.travel.service;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class UserTravelServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("1. user 회원가입")
    public void test1(){
        boolean join = true;
        List<UserTravel>  list = new ArrayList<>();
        for (int i =0; i < 5; i++){
            UserTravel u = UserTravel.builder()
                    .userId("user2"+i)
                    .userEmail("user2"+i+"@naver.com")
                    .password("qwer1234")
                    .name("사용자")
                    .userBirthday("19930809")
                    .userGender("여")
                    .userPhone("010-1111-1111")
                    .address("테스트")
                    .addressPostcode("테스트")
                    .addressDetail("테스트")
                    .addressExtra("테스트")
                    .userSocial(false)
                    .userAgree(true)
                    .build();

            list.add(u);
        }


        // 저장확인
        list.stream().forEach(i-> {
            UserDTO userDTO = userService.entityToDto(i);
            userService.userSave(userDTO);
            System.out.println(i);
        });


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
        int result = userService.userGetId("김윤하");
        System.out.println(result);
    }


    @Test
    @DisplayName("3. user 정보 수정")
    public void test3(){

    }


    @Test
    @DisplayName("3. user 패스워드 수정")
    public void test4(){
        UserDTO dto = UserDTO.builder()
                .userId("user1")
                .userEmail("admin@naver.com")
                .password("user1234")
                .name("관리자")
                .build();

        String result = userService.userPasswordModify(dto);
        System.out.println(result);

    }



}