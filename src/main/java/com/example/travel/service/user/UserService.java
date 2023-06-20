package com.example.travel.service.user;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.dto.user.UserResponseDTO;
import com.example.travel.security.dto.UserTravelDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface UserService {
    public UserTravel userSave(UserDTO userSaveDTO); // user 저장
    public UserDTO userGetNo(Long no); // user 가져오기
    public int userGetId(String id); // user id 체크
    public Boolean userGetEmail(String email); // user Email 체크

    public String userPasswordModify(UserDTO userDTO); //회원 비밀번호 변경

    public boolean userModitfy(UserDTO userSaveDTO); //회원정보 수정

    public boolean userDelete(String id, String pw);


    public String userGetName(String name,String email); // 아이디 찾기
    public UserDTO userGetPassword(String id,String name,String email); // 비밀번호 찾기

    @Transactional
    public UserTravel userInfo(UserDTO userDTO);


    //마이페이지=======================
    //회원 프로필 변경
    public String userProfileImage(UserDTO userDTO);


    //entity - dto  : 받는 값
    default UserTravel dtoToEntity(UserDTO dto){
        UserTravel result = UserTravel.builder()
                .userNo(dto.getUserNo())
                .userId(dto.getUserId())
                .userEmail(dto.getUserEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .userBirthday(dto.getUserBirthday())
                .userGender(dto.getUserGender())
                .userPhone(dto.getUserPhone())
                //.userImg((UserImage)dto.getUserImg())
                .address(dto.getAddress())
                .addressPostcode(dto.getAddressPostcode())
                .addressDetail(dto.getAddressDetail())
                .addressExtra(dto.getAddressExtra())
                .userAgree(dto.getUserAgree())
                .userSocial(dto.getUserSocial())
                .build();

        System.out.println("role ---------------");
        Set<UserRole> roleSet = dto.getRoleSet();
        roleSet.stream().forEach(userRole -> {
            result.roleAdd(userRole);
        });


        return result;
    }

    default UserDTO entityToDto(UserTravel userTravel){
        UserDTO result = UserDTO.builder()
                .userNo(userTravel.getUserNo())
                .userId(userTravel.getUserId())
                .userEmail(userTravel.getUserEmail())
                .password(userTravel.getPassword())
                .name(userTravel.getName())
                .userBirthday(userTravel.getUserBirthday())
                .userGender(userTravel.getUserGender())
                .userPhone(userTravel.getUserPhone())
                //.userImg((MultipartFile)userTravel.getUserImg())
                .address(userTravel.getAddress())
                .addressPostcode(userTravel.getAddressPostcode())
                .addressDetail(userTravel.getAddressDetail())
                .addressExtra(userTravel.getAddressExtra())
                .userSocial(userTravel.getUserSocial())
                .build();

        System.out.println("role ---------------");
        Set<UserRole> roleSet = userTravel.getRoleSet();
        roleSet.stream().forEach(userRole -> {
            result.roleAdd(userRole);
        });

        return result;
    }


    //entity - ResponseDto : 전달 값
    default UserResponseDTO entityToResponseDto(UserTravel userTravel){
        UserResponseDTO result = UserResponseDTO.builder()
                .userNo(userTravel.getUserNo())
                .userId(userTravel.getUserId())
                .userEmail(userTravel.getUserEmail())
                .password(userTravel.getPassword())
                .name(userTravel.getName())
                .userBirthday(userTravel.getUserBirthday())
                .userGender(userTravel.getUserGender())
                .userPhone(userTravel.getUserPhone())
                .userImg(userTravel.getUserImg())
                .address(userTravel.getAddress())
                .addressPostcode(userTravel.getAddressPostcode())
                .addressDetail(userTravel.getAddressDetail())
                .addressExtra(userTravel.getAddressExtra())
                .userSocial(userTravel.getUserSocial())
                .build();

        System.out.println("role ---------------");
        Set<UserRole> roleSet = userTravel.getRoleSet();
        roleSet.stream().forEach(userRole -> {
            result.roleAdd(userRole);
        });

        return result;
    }

    default UserTravel responseDtoToEntity(UserResponseDTO dto){
        UserTravel result = UserTravel.builder()
                .userNo(dto.getUserNo())
                .userId(dto.getUserId())
                .userEmail(dto.getUserEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .userBirthday(dto.getUserBirthday())
                .userGender(dto.getUserGender())
                .userPhone(dto.getUserPhone())
                .userImg((UserImage)dto.getUserImg())
                .address(dto.getAddress())
                .addressPostcode(dto.getAddressPostcode())
                .addressDetail(dto.getAddressDetail())
                .addressExtra(dto.getAddressExtra())
                .userAgree(dto.getUserAgree())
                .userSocial(dto.getUserSocial())
                .build();

        System.out.println("role ---------------");
        Set<UserRole> roleSet = dto.getRoleSet();
        roleSet.stream().forEach(userRole -> {
            result.roleAdd(userRole);
        });


        return result;
    }



}
