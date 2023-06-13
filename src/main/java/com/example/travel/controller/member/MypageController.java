package com.example.travel.controller.member;

import com.example.travel.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//        String userName = principal.getName();
//        int i = userService.userGetId(userName);

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지

        if(flashMap!=null) {
            model.addAttribute("login",flashMap.get("login"));
        }

        return "mypage/main";
    }
}
