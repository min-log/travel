package com.example.travel.service.user;

import com.example.travel.domain.User;
import com.example.travel.dto.user.UserDTO;

public interface UserService {
    public User userSave(UserDTO userDTO); // user 저장
    public UserDTO userGetNo(Long no); // user 가져오기
    public UserDTO userModitfy(UserDTO userDTO);


    default User dtoToEntity(UserDTO userDTO){
        return User.builder()
                .userNo(userDTO.getUserNo())
                .userId(userDTO.getUserId())
                .userEmail(userDTO.getUserEmail())
                .userPassword(userDTO.getUserPassword())
                .userName(userDTO.getUserName())
                .userBirthday(userDTO.getUserBirthday())
                .userGender(userDTO.getUserGender())
                .userPhone(userDTO.getUserPhone())
                .userAddress(userDTO.getUserAddress())
                .userImg(userDTO.getUserImg())
                .build();
    }

    default UserDTO entityToDto(User user){
        return UserDTO.builder()
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
