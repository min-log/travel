package com.example.travel.controller;

import com.example.travel.domain.UserImage;
import com.example.travel.repository.member.UserImageRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {


    final FileService service;
    final UserImageRepository userImageRepository;


    @GetMapping("/security-login")
    public String securityLogin(@AuthenticationPrincipal UserTravelAdapter user,
                                HttpSession session){
        log.info("회원 로그인 여부에 따른 세션");
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
                if (path == null){
                    img = originFileName;
                }
                user.setProfile(img);
            }else {
                log.info("이미지 없음");
            }
            session.setAttribute("userT",user);
        }else{
            log.info("로그아웃");
            session.removeAttribute("userT");
        }
        return "redirect:/main";
    }

    @GetMapping(value = {"","main"})
    public String main(
            HttpServletRequest request,
            Model model
      ){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }


        return "pages/index";
    }



}
