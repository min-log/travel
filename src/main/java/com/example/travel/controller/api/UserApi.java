package com.example.travel.controller.api;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/userApi")
public class UserApi {
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    private final UserService userService;
    final PasswordEncoder passwordEncoder;


    @Transactional
    @GetMapping("/userPassword")
    public Boolean userPassword(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw
    ){
        log.info("id"+ id);
        log.info("pw"+ pw);
        UserDTO build = UserDTO.builder().userId(id).build();
        UserTravel userTravel = userService.userInfo(build);

        String pwEncode = userTravel.getPassword();
        boolean matches = passwordEncoder.matches(pw,pwEncode);
        log.info(matches);

        return matches;
    }

    @GetMapping("/idCheck")
    public Boolean idCheck(@RequestParam("id") String id){
        int i = userService.userGetId(id);
        log.info(i);
        if (i > 0){
            return false;
        }else{
            return true;
        }

    }


    @GetMapping("/emailCheck")
    public Boolean emailCheck(@RequestParam("userEmail") String userEmail){
        boolean result = userService.userGetEmail(userEmail);
        return result;

    }

    @GetMapping("/tag")
    public List<String> tagList(){
        log.info("회원 아이디 리스트 전달");
        List<String> tagList = userService.userList();
        return tagList;
    }




    @GetMapping("/profileImg")
    public ResponseEntity<byte[]> profileImgGet(@RequestParam(value = "no") Long no){
        log.info("프로필 이미지 불러오기");
        ResponseEntity<byte[]> result = null;
        log.info("회원 no : {}",no);
        UserImage userImage = userService.userGetProfileImage(no);
        String fileOriginName = userImage.getOriginFileName();
        String path = userImage.getPath();
        File file;

        try{
            if (path == null){
                file = new File(uploadPath + File.separator + "profile_img.png");
                log.info("file : " + file);

                HttpHeaders headers = new HttpHeaders();
                //MIME타입 처리
                headers.add("Content-Type" , Files.probeContentType(file.toPath()));
                // 파일 데이터처리
                result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

            } else {
                file = new File(uploadPath + File.separator + path + File.separator + fileOriginName);
                log.info("file : " + file);
                HttpHeaders headers = new HttpHeaders();
                //MIME타입 처리
                headers.add("Content-Type" , Files.probeContentType(file.toPath()));
                // 파일 데이터처리
                result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
            }


        }catch ( Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



        return result;

    }


}
