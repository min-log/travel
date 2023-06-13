package com.example.travel.service.user;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.ImageDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserImageRepository;
import com.example.travel.repository.UserRepository;
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
    public UserTravel userSave(UserDTO userDto) {
        log.info("userSaveDTO : {}" , userDto);
        // 일반회원 가입
        userDto.setUserSocial(false);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // 패스워드 암호화
        UserTravel entity = dtoToEntity(userDto); //entity 변경
        entity.roleAdd(UserRole.USER); // 권한 추가

        System.out.println();
        log.info("=======================");
        log.info("이미지 파일이 있을때 : " + userDto.getUserImg());
        if(!(userDto.getUserImg() == null)) {
            log.info("이미지 파일이 있을때");
            UserImage userImage = saveMemberImage(userDto.getUserImg());
            entity.updateUserImage(userImage);
        }

        UserTravel result = userRepository.save(entity);


        log.info("result : {}" , result);




        return result;
    }



    //이미지 관련
    @Transactional(readOnly = false)
    UserImage saveMemberImage(MultipartFile file) {
        if(file.getContentType().startsWith("image") == false) {
            log.warn("이미지 파일이 아닙니다.");
            return null;
        }

        String originalName = file.getOriginalFilename();
        Path root = Paths.get(uploadPath, "member");

        try {
            ImageDTO imageDTO =  fileService.createImageDTO(originalName, root);
            UserImage memberImage = UserImage.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(imageDTO.getFileUrl())
                    .build();

            file.transferTo(Paths.get(imageDTO.getFileUrl()));

            return userImageRepository.save(memberImage);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
        }

        return null;
    }


    @Override
    public UserDTO userGetNo(Long no) {
        UserTravel entity = userRepository.getUserTravelByUserNo(no);
        return entityToDto(entity);
    }

    @Override
    public int userGetId(String id) {
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
        Optional<UserTravel> result = userRepository.getUserTravelByUserEmail(email);
        log.info("이메일 존재 : true  | 이메일 무 존재 : false");
        if (result.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public String userPasswordModify(UserDTO userDTO) {
        log.info("비밀번호 수정 ==========================");

        String name = userDTO.getName();
        String userEmail = userDTO.getUserEmail();
        log.info(name);
        log.info(userEmail);

        Optional<UserTravel> i = userRepository.getUserByNameAndUserEmail(name, userEmail);
        if (i.isPresent()){
            UserTravel userTravel = i.get();
            log.info("존재하는 : {}",i);
            UserDTO dto = entityToDto(userTravel);
            log.info("권한 확인: " + userTravel.getRoleSet());
            dto.setPassword(passwordEncoder.encode(userDTO.getPassword())); // 패스워드 암호화
            UserTravel entity = dtoToEntity(dto); //entity 변경
            UserTravel save = userRepository.save(dtoToEntity(dto));

            return dto.getUserId();
        }else{
            log.info("존재 없는");
            return null;
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
}
