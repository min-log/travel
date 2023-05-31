package com.example.travel.service.user;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;

public interface UserService {
    public UserTravel userSave(UserSaveDTO userSaveDTO); // user 저장
    public UserSaveResultDTO userGetNo(Long no); // user 가져오기
    public int userGetId(String id); // user id 체크
    public UserSaveResultDTO userModitfy(UserSaveDTO userSaveDTO);


    default UserTravel dtoToEntity(UserSaveDTO dto){
        UserTravel result = UserTravel.builder()
                .userNo(dto.getUserNo())
                .userId(dto.getUserId())
                .userEmail(dto.getUserEmail())
                .password(dto.getPassword())
                .userName(dto.getUserName())
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

    default UserSaveResultDTO entityToDto(UserTravel userTravel){
        return UserSaveResultDTO.builder()
                .userNo(userTravel.getUserNo())
                .userId(userTravel.getUserId())
                .userEmail(userTravel.getUserEmail())
                .password(userTravel.getPassword())
                .userName(userTravel.getUserName())
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
