package com.example.travel.controller;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserImageRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {


    final FileService service;
    final UserImageRepository userImageRepository;



    @GetMapping(value = {"","main"})
    public String main(
            @AuthenticationPrincipal UserTravelAdapter user,
            HttpSession session,
            HttpServletRequest request,
            Model model
      ){

        //log.info("회원 로그인 성공 여부에 따른 쿠키 제거");
        if (user != null){
            log.info("로그인"); String profile = user.getProfile();
            String img=null;

            Optional<UserImage> byOriginFileName = userImageRepository.findByOriginFileName(profile);
            if (byOriginFileName.isPresent()){
                UserImage userImage = byOriginFileName.get();
                String path = userImage.getPath();
                String originFileName = userImage.getOriginFileName();
                String uuid = userImage.getUuid();
                img = "\\upload\\" +path +"\\"+ uuid +"_"+ originFileName;
                user.setProfile(img);
            }else {
                log.info("없음");
            }
            session.setAttribute("userT",user);
        }else{
            log.info("로그아웃");
            session.removeAttribute("user");
        }

        // 모달 회원 탈퇴 알럿
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }


        return "pages/index";
    }



}
