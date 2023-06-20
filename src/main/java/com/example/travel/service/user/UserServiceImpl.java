package com.example.travel.service.user;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.ImageDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.dto.user.UserResponseDTO;
import com.example.travel.repository.UserImageRepository;
import com.example.travel.repository.UserRepository;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.security.service.UserTravelDetailsService;
import com.example.travel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    // 이미지 관련 추가
    private final FileService fileService;
    private final UserImageRepository userImageRepository;


    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    @Transactional
    public UserTravel userSave(UserDTO userDto) {
        log.info("user 객체 저장 및 반환 ==========================");
        log.info("userSaveDTO : {}" , userDto);
        // 일반회원 가입
        userDto.setUserSocial(false);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // 패스워드 암호화
        UserTravel entity = dtoToEntity(userDto); //entity 변경
        entity.roleAdd(UserRole.USER); // 권한 추가

        System.out.println();
        log.info("getUserImg : " + userDto.getUserImg().getName());
        MultipartFile userImg = userDto.getUserImg();
        log.info("저장하자");
        UserImage imageDTO = fileService.createImageDTO(userImg);

        if(imageDTO != null) {
            UserImage save = userImageRepository.save(imageDTO);
            log.info("저장됨");
            entity.updateUserImage(save);
        }

        UserTravel result = userRepository.save(entity);
        return result;
    }





    @Override
    public UserDTO userGetNo(Long no) {
        log.info("user 고유번호로 존재 유무확인 ==========================");
        UserTravel entity = userRepository.getUserTravelByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public int userGetId(String id) {
        log.info("아이디 존재 유무확인 ==========================");
        try {
            UserTravel result = userRepository.getUserTravelByUserId(id);
            log.info("뽑아진 ID 정보: {}",result);
            if ( result == null){
                //성공
                return 0;
            }else{
                //실패
                return 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 5;
    }

    @Override
    public Boolean userGetEmail(String email) {
        log.info("이메일 존재 유무확인 ==========================");
        Optional<UserTravel> result = userRepository.getUserTravelByUserEmail(email);
        log.info("이메일 존재 : true  | 이메일 무 존재 : false");
        if (result.isPresent()){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public String userPasswordModify(UserDTO userDTO) {
        log.info("비밀번호 수정 ==========================");

        String name = userDTO.getName();
        String userEmail = userDTO.getUserEmail();
        String userId = userDTO.getUserId();
        log.info(name);
        log.info(userId);

        try {
                Optional<UserTravel> i = userRepository.getUserPullByUserId(userId);
                if (i.isPresent()){
                    log.info("USER가 존재할 경우 : {}",i);
                    log.info(i.get().getUserImg().getOriginFileName());
                    log.info(i.get().getUserId());
                    log.info(i.get().getUserId());

                    UserTravel userTravel = i.get();
                    UserResponseDTO userResponseDTO = entityToResponseDto(userTravel);
                    userResponseDTO.setPassword(passwordEncoder.encode(userDTO.getPassword())); // 패스워드 암호화

                    UserTravel entity = responseDtoToEntity(userResponseDTO); //entity 변경
                    UserTravel save = userRepository.save(entity);

                    return save.getUserId();
                }else{
                    log.info("존재 없는");
                    return null;
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public boolean userModitfy(UserDTO userSaveDTO) {

        UserTravel userTravel = userInfo(userSaveDTO);
        UserResponseDTO userResponseDTO = entityToResponseDto(userTravel);
        userResponseDTO.setName(userSaveDTO.getName());
        userResponseDTO.setUserBirthday(userSaveDTO.getUserBirthday());
        userResponseDTO.setUserGender(userSaveDTO.getUserGender());
        userResponseDTO.setUserPhone(userSaveDTO.getUserPhone());
        userResponseDTO.setAddress(userSaveDTO.getAddress());
        userResponseDTO.setAddressExtra(userSaveDTO.getAddressExtra());
        userResponseDTO.setAddressDetail(userSaveDTO.getAddressDetail());
        userResponseDTO.setAddressPostcode(userSaveDTO.getAddressPostcode());
        log.info("userResponseDTO : {}",userResponseDTO);
        if(userSaveDTO.getPassword() != null){
            userResponseDTO.setPassword(passwordEncoder.encode(userSaveDTO.getPassword()));
        }
        UserTravel entity = responseDtoToEntity(userResponseDTO);
        log.info("============ 저장될 앤티티");
        log.info(entity);
        UserTravel result = userRepository.save(entity);
        log.info("result: {}",result);
        if (result.getUserId() == entity.getUserId()){
            return true;
        }else{
            return false;
        }

    }


    @Transactional
    @Override
    public boolean userDelete(String id, String pw) {
        UserDTO dto = UserDTO.builder().userId(id).build();
        UserTravel userTravel = userInfo(dto);
        Long userNo = userTravel.getUserNo();
        log.info(userNo);
        String password = userTravel.getPassword();
        boolean matches = passwordEncoder.matches(pw, password);
        if (! matches){
            return false;
        }

        log.info("존재하는 회원");
        UserImage userImg = userTravel.getUserImg();
        log.info(userImg.getId());

       boolean b = userImageRepository.deleteByUserImage(userImg.getId());
        log.info(2);
        int result = userRepository.removeUserTravelByUserNo(userNo);
        log.info(3);

        return  false;




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
        try {
            if (result.isPresent()){
                log.info("result.get() : "+result.get());
                UserTravel entity = result.get();
                UserDTO userDTO = entityToDto(entity);
                //값이 있을경우
                return userDTO;
            }else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            log.info("에러 메시지2"+e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public UserTravel userInfo(UserDTO userDTO) {
        try {

            Optional<UserTravel> result = userRepository.getUserPullByUserId(userDTO.getUserId());

            if (result.isPresent()) {
                UserTravel userTravel = result.get();
                return userTravel;
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Transactional
    @Override
    public String userProfileImage(UserDTO userDto) {
        String userId = userDto.getUserId();
        Optional<UserTravel> userPullByUser = userRepository.getUserPullByUserId(userId);

        if (userPullByUser.isPresent()){
            UserTravel userTravel = userPullByUser.get();

            if(!(userDto.getUserImg() == null)) {
                MultipartFile userImg = userDto.getUserImg();
                log.info("저장하자");

                UserImage imageDTO = fileService.createImageDTO(userImg);
                UserImage save = userImageRepository.save(imageDTO);
                log.info("저장됨");
                userTravel.updateUserImage(save);
            }


            UserTravel result = userRepository.save(userTravel);

            return result.getUserImg().getOriginFileName();
        }else{
            return null;
        }
    }
}
