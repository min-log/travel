package com.example.travel.repository;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;



    @Test
    @DisplayName("1. 회원가입")
    public void join(){
        UserDTO userDTO = UserDTO.builder().userNo(4L)
                .userId("dizel93")
                .name("dizel93")
                .userEmail("dizel93@naver.com")
                .userGender("여")
                .userPhone("011-1111-1111")
                .address("Aaaaaa")
                .addressExtra("aa")
                .addressDetail("111")
                .addressPostcode("1111")
                .password("1111")
                .userSocial(false)
                .build();

        userDTO.roleAdd(UserRole.USER); // 권한 추가
        userDTO.roleAdd(UserRole.ADMIN);


        UserTravel userTravel = userService.userSave(userDTO);
        System.out.println(userTravel);
        Set<UserRole> roleSet = userTravel.getRoleSet();
        System.out.println(roleSet);


        UserDTO result = UserDTO.builder().userNo(userTravel.getUserNo())
                .userId(userTravel.getUserId())
                .name(userTravel.getName())
                .userEmail(userTravel.getUserEmail())
                .userGender(userTravel.getUserGender())
                .userPhone(userTravel.getUserPhone())
                .address(userTravel.getAddress())
                .addressExtra(userTravel.getAddressExtra())
                .addressDetail(userTravel.getAddressDetail())
                .addressPostcode(userTravel.getAddressPostcode())
                .password(passwordEncoder.encode(userTravel.getPassword()))
                .userSocial(userDTO.getUserSocial())
                .build();
        roleSet.stream().forEach(userRole -> {
            result.roleAdd(userRole);
        });
        System.out.println(result);
        System.out.println(result.getRoleSet());
        //result.roleAdd(roleSet);



    }


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


    @Test
    @DisplayName("2. 회원확인 - 아이디 찾기")
    public void test2() {

        Optional<UserTravel> result = userRepository.getUserByNameAndUserEmail("관리자1", "jimin-log@naver.com");
        if (result.isPresent()){
            System.out.println("있음");
            System.out.println(result.get());
        }else{
            System.out.println("없음");
        }

    }


    @Test
    @DisplayName("3. 회원확인 - 비밀번호찾기")
    public void test3() {

        Optional<UserTravel> result = userRepository.getUserByPasswordAndUserEmail("wwoo", "김윤하", "jimin-log@naver.com");
        System.out.println(result.get());

    }


}
