package com.example.travel.service.user;

import com.example.travel.domain.User;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;


    @Override
    public User userSave(UserDTO userDTO) {
        User entity = dtoToEntity(userDTO);
        User result = userRepository.save(entity);
        return result;
    }

    @Override
    public UserDTO userGetNo(Long no) {
        User entity = userRepository.getUserByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public UserDTO userModitfy(UserDTO userDTO) {

        UserDTO dto = UserDTO.builder()
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
        User entity = dtoToEntity(dto);
        User entitySave = userRepository.save(entity);

        return entityToDto(entitySave);
    }
}
