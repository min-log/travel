package com.example.travel.service.user;

import com.example.travel.domain.User;
import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;


    @Override
    public User userSave(UserSaveDTO userSaveDTO) {
        log.info("userSaveDTO : {}" , userSaveDTO);
        User entity = dtoToEntity(userSaveDTO);
        User result = userRepository.save(entity);
        log.info("result : {}" , result);
        return result;
    }

    @Override
    public UserSaveResultDTO userGetNo(Long no) {
        User entity = userRepository.getUserByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public UserSaveResultDTO userModitfy(UserSaveDTO userSaveDTO) {

        UserSaveDTO dto = UserSaveDTO.builder()
                .userNo(userSaveDTO.getUserNo())
                .userId(userSaveDTO.getUserId())
                .userEmail(userSaveDTO.getUserEmail())
                .userPassword(userSaveDTO.getUserPassword())
                .userName(userSaveDTO.getUserName())
                .userBirthday(userSaveDTO.getUserBirthday())
                .userGender(userSaveDTO.getUserGender())
                .userPhone(userSaveDTO.getUserPhone())
                .userAddress(userSaveDTO.getUserAddress())
                .userImg(userSaveDTO.getUserImg())
                .build();
        User entity = dtoToEntity(dto);
        User entitySave = userRepository.save(entity);

        return entityToDto(entitySave);
    }
}
