package com.example.travel.service.user;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

//        UserDTO result = UserDTO.builder()
//                .userNo(jimin.getUserNo())
//                .userId(jimin.getUserId())
//                .userEmail(jimin.getUserEmail())
//                .password(jimin.getPassword())
//                .name(jimin.getName())
//                .userBirthday(jimin.getUserBirthday())
//                .userGender(jimin.getUserGender())
//                .userPhone(jimin.getUserPhone())
//                .userImg(jimin.getUserImg())
//                .address(jimin.getAddress())
//                .addressPostcode(jimin.getAddressPostcode())
//                .addressDetail(jimin.getAddressDetail())
//                .addressExtra(jimin.getAddressExtra())
//                .build();
//
//
//        Set<UserRole> roleSet = jimin.getRoleSet();
//        System.out.println(roleSet);

//        roleSet.stream().forEach(i->{
//            System.out.println(i);
//        });



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
}