package com.example.travel.controller.member;

import com.example.travel.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import java.security.Principal;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserService  userService;
    @GetMapping("")
    public String myPage(Principal principal ,Cookie cookie){
        String userName = principal.getName();
        int i = userService.userGetId(userName);
        ;
        log.info(cookie.getName());
        log.info(principal.getName());

        return "mypage/main";
    }
}
