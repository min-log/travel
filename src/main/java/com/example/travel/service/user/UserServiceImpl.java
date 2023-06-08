package com.example.travel.service.user;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        log.info("회원정보 수정 =====================");
        UserDTO dto = UserDTO.builder()
                .userNo(userSaveDTO.getUserNo())
                .userId(userSaveDTO.getUserId())
                .userEmail(userSaveDTO.getUserEmail())
                .password(userSaveDTO.getPassword())
                .name(userSaveDTO.getName())
                .userBirthday(userSaveDTO.getUserBirthday())
                .userGender(userSaveDTO.getUserGender())
                .userPhone(userSaveDTO.getUserPhone())
                .address(userSaveDTO.getAddress())
                .addressPostcode(userSaveDTO.getAddressPostcode())
                .addressDetail(userSaveDTO.getAddressDetail())
                .addressExtra(userSaveDTO.getAddressExtra())
                .userImg(userSaveDTO.getUserImg())
                .build();

        log.info("dto : {}",dto);
        UserTravel entity = dtoToEntity(dto);
        log.info("entity : {}",entity);
        UserTravel entitySave = userRepository.save(entity);
        log.info("entitySave : {}",entitySave);
        return entityToDto(entitySave);
    }

    //아이디 찾기
    @Override
    public String userGetName(String name, String email) {
        Optional<UserTravel> result = userRepository.getUserByNameAndUserEmail(name, email);
        if (result.isPresent()){
            //값이 있을경우
            return result.get().getUserId();
        }else{
            // 값이 없을 경우
           // throw new IllegalArgumentException("No user found with the given name and email.")
            return null;
        }

    }

    //비밀번호 찾기
    @Override
    public UserDTO userGetPassword(String id, String name, String email) {
        Optional<UserTravel> result = userRepository.getUserByPasswordAndUserEmail(id, name, email);
        log.info("result.get() : "+result.get());
        if (result.isPresent()){
            UserTravel entity = result.get();
            UserDTO userDTO = entityToDto(entity);
            //값이 있을경우
            return userDTO;
        }else{
            // 값이 없을 경우
            // throw new IllegalArgumentException("No user found with the given name and email.")
            return null;
        }
    }
}
