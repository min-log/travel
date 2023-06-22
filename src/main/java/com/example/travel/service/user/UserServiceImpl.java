package com.example.travel.service.user;

import com.example.travel.domain.Image;
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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 관련 추가
    private final FileService fileService;
    private final UserImageRepository userImageRepository;


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
        UserImage save = userImageRepository.save(imageDTO);
        entity.updateUserImage(save);

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
        Long userNo = userTravel.getUserNo(); //user 고유번호
        log.info(userNo);
        String password = userTravel.getPassword();
        boolean matches = passwordEncoder.matches(pw, password);
        log.info(matches);
        if (!matches){
            return false;
        }

        log.info("존재하는 회원");
        
        UserImage userImg = userTravel.getUserImg();
        String originFileName = userImg.getOriginFileName();
        String uuid = userImg.getUuid();
        String path = userImg.getPath();
        String fileName = path + "\\" + uuid + "_" +originFileName;

        log.info(userImg.getId());
        int deleteRole = userRepository.deleteByUserRole(userNo); // user권한제거
        System.out.println(deleteRole);
        int deleteUser = userRepository.deleteByUserId(id); // user제거
        System.out.println(deleteUser);
        fileService.removeFile(fileName);//실제 이미지 제거
        int deleteImage = userImageRepository.deleteByUserImage(userNo);// user이미지 제거
        System.out.println(deleteImage);

        if (deleteRole > 0 && deleteUser > 0 && deleteImage > 0){
            return  true;
        }else {
            return  false;
        }

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
        log.info("userProfileImage ----------------------");
        log.info(0);
        UserTravel userTravelByUserId = userRepository.getUserTravelByUserId(userDto.getUserId());
        Long id = userTravelByUserId.getUserImg().getId();

        UserImage userImage = new UserImage();
        userImage.setId(id);
        MultipartFile userImg = userDto.getUserImg();

        //새로운 이미지
        UserImage image = fileService.createImageDTO(userImg);
        image.setId(id);
        log.info("image : {}",image);
        UserImage save = userImageRepository.save(image);

        userTravelByUserId.updateUserImage(save);
        UserTravel result = userRepository.save(userTravelByUserId);

        log.info(result);
        return result.getUserId();
    }

    @Override
    public List<String> userList() {
        List<String> userTravelList = userRepository.getUserTravelList();
        return userTravelList;
    }

}
