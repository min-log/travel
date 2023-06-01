package com.example.travel.service.user;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserTravel userSave(UserDTO userSaveDTO) {
        log.info("userSaveDTO : {}" , userSaveDTO);
        // 일반회원 가입
        userSaveDTO.setUserSocial(false);
        userSaveDTO.setPassword(passwordEncoder.encode(userSaveDTO.getPassword())); // 패스워드 암호화
        UserTravel entity = dtoToEntity(userSaveDTO); //entity 변경
        entity.roleAdd(UserRole.USER); // 권한 추가


        UserTravel result = userRepository.save(entity);
        log.info("result : {}" , result);
        return result;
    }

    @Override
    public UserDTO userGetNo(Long no) {
        UserTravel entity = userRepository.getUserByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public int userGetId(String id) {
        UserTravel result = userRepository.getUserByUserId(id);
        log.info("뽑아진 ID 정보: {}",result);
        if ( result == null){
            //성공
            return 0;
        }else{
            //실패
            return 5;
        }

    }

    @Override
    public UserDTO userModitfy(UserDTO userSaveDTO) {

        UserDTO dto = UserDTO.builder()
                .userNo(userSaveDTO.getUserNo())
                .userId(userSaveDTO.getUserId())
                .userEmail(userSaveDTO.getUserEmail())
                .password(userSaveDTO.getPassword())
                .userName(userSaveDTO.getUserName())
                .userBirthday(userSaveDTO.getUserBirthday())
                .userGender(userSaveDTO.getUserGender())
                .userPhone(userSaveDTO.getUserPhone())
                .address(userSaveDTO.getAddress())
                .addressPostcode(userSaveDTO.getAddressPostcode())
                .addressDetail(userSaveDTO.getAddressDetail())
                .addressExtra(userSaveDTO.getAddressExtra())
                .userImg(userSaveDTO.getUserImg())
                .build();


        UserTravel entity = dtoToEntity(dto);
        UserTravel entitySave = userRepository.save(entity);

        return entityToDto(entitySave);
    }
}
