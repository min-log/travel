package com.example.travel.controller;

import com.example.travel.domain.UserTravel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping(value = {"","main"})
    public String main(@RequestParam(value = "login",required = false) Boolean login, RedirectAttributes redirectAttributes){
        log.info("login {}" ,login);

        redirectAttributes.addFlashAttribute("login","로그인이 성공했습니다."); //로그인 상태일 때 실행


        return "pages/index";
    }
}
