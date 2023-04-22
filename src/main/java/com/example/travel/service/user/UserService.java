package com.example.travel.service.user;

import com.example.travel.domain.User;
import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;

public interface UserService {
    public User userSave(UserSaveDTO userSaveDTO); // user 저장
    public UserSaveResultDTO userGetNo(Long no); // user 가져오기
    public UserSaveResultDTO userModitfy(UserSaveDTO userSaveDTO);


    default User dtoToEntity(UserSaveDTO dto){
        return User.builder()
                .userNo(dto.getUserNo())
                .userId(dto.getUserId())
                .userEmail(dto.getUserEmail())
                .userPassword(dto.getUserPassword())
                .userName(dto.getUserName())
                .userBirthday(dto.getUserBirthday())
                .userGender(dto.getUserGender())
                .userPhone(dto.getUserPhone())
                .userAddress(dto.getUserAddress())
                .userImg(dto.getUserImg())
                .build();
    }

    default UserSaveResultDTO entityToDto(User user){
        return UserSaveResultDTO.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userName(user.getUserName())
                .userBirthday(user.getUserBirthday())
                .userGender(user.getUserGender())
                .userPhone(user.getUserPhone())
                .userAddress(user.getUserAddress())
                .userImg(user.getUserImg())
                .build();
    }


}
