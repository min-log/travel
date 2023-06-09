package com.example.travel.service.user;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;

public interface UserService {
    public UserTravel userSave(UserDTO userSaveDTO); // user 저장
    public UserDTO userGetNo(Long no); // user 가져오기
    public int userGetId(String id); // user id 체크
    public Boolean userGetEmail(String email);
    public UserDTO userModitfy(UserDTO userSaveDTO);


    public String userGetName(String name,String email); // 아이디 찾기
    public UserDTO userGetPassword(String id,String name,String email); // 비밀번호 찾기



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
                .userImg(dto.getUserImg())
                .address(dto.getAddress())
                .addressPostcode(dto.getAddressPostcode())
                .addressDetail(dto.getAddressDetail())
                .addressExtra(dto.getAddressExtra())
                .userAgree(dto.getUserAgree())
                .userSocial(dto.getUserSocial())
                .build();



        return result;
    }

    default UserDTO entityToDto(UserTravel userTravel){
        return UserDTO.builder()
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
                .build();
    }


}
