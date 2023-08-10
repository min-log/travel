package com.example.travel.service.user;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.Graph;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.dto.user.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface UserService {
    public UserTravel userSave(UserDTO userSaveDTO); // user 저장
    public UserTravel userAdminSave(UserDTO userSaveDTO); // user 관리자 저장

    public UserDTO userGetNo(Long no); // user 가져오기
    public int userGetId(String id); // user id 체크
    public Boolean userGetEmail(String email); // user Email 체크

    public String userPasswordModify(UserDTO userDTO); //회원 비밀번호 변경

    public boolean userModitfy(UserDTO userSaveDTO); //회원정보 수정

    public boolean userDelete(String id, String pw); // 회원 인증 후 삭제

    public boolean userDeleteNo(Long no); //관리자 회원 삭제


    public String userGetName(String name,String email); // 아이디 찾기
    public UserDTO userGetPassword(String id,String name,String email); // 비밀번호 찾기

    public UserImage userGetProfileImage(Long no);

    @Transactional
    public UserTravel userInfo(UserDTO userDTO);


    //마이페이지=======================
    //회원 프로필 변경
    public String userProfileImage(UserDTO userDTO);



    //고객 리스트
    public List<String> userList();

    //관리자 페이지  ------------------------------
    public Page<UserDTO> userListAdmin(Integer size,Integer page, String order, String keyword,Integer role);

    public List<Graph> userGenderGraph();
    public List<Graph> userAgeGraph();

    public int userTotal();




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
                .userAge(dto.getUserAge())
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
                .userAge(userTravel.getUserAge())
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
