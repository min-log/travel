package com.example.travel.service.user;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.member.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Test
    @DisplayName("entity -> Dto")
    public void test1(){

        //UserDTO dto = userService.userGetNo(1L);
        UserTravel entity = userRepository.getUserTravelByUserId("jimin");
        System.out.println(entity.getRoleSet());


    }


    @Test
    @DisplayName("회원정보 수정")
    public void test2(){
        UserDTO dt = UserDTO.builder()
                .userId("ddd")
                .name("ddd")
                .userEmail("ddd@naver.com")
                .password("1212")
                .build();
        String s = userService.userPasswordModify(dt);
        System.out.println(s);
    }




    @Test
    void userDelete() {
        boolean b = userService.userDelete("dizel93", "qwer1234");
    }
}