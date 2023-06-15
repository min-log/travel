package com.example.travel.controller;

import com.example.travel.dto.user.UserDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/")
public class MainController {





    @GetMapping(value = {"","main"})
    public String main(
            @AuthenticationPrincipal UserTravelAdapter user,
            HttpSession session
    ){

        log.info("회원 로그인 성공 여부에 따른 쿠키 제거");


        if (user != null){
            log.info("로그인");
            log.info(user.getUserId());
            log.info(user.getProfile());
            session.setAttribute("userT",user);
        }else{
            log.info("로그아웃");
            session.removeAttribute("user");
        }


        return "pages/index";
    }
}
