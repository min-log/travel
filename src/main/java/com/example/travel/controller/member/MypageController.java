package com.example.travel.controller.member;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserService  userService;
    @GetMapping("")
    public String myPage(HttpServletRequest request, Model model){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("login",flashMap.get("login"));
        }

        return "mypage/main";
    }


    @GetMapping("/userImageModify")
    public String userImageModify(Model model){
        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요

        return "mypage/userImageModify";
    }

    @PostMapping("/userImageModify")
    public String userImageModifyForm(@ModelAttribute("userTravel") UserDTO user,
                                      BindingResult bindingResult,
                                      HttpServletRequest request){
        log.info("프로필 이미지 변경==============================");


        return "mypage/userImageModify";
    }



}
