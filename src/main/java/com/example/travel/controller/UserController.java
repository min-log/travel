package com.example.travel.controller;


import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Log4j2
@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String userJoinForm(){
        return "member/join";
    }

    @PostMapping("/join")
    public String userJoin(Model model,@Valid UserSaveDTO user ,
                           BindingResult bindingResult){
        System.out.println("start" + user);
        model.addAttribute("user",user);


        // 실패 로직
        if (bindingResult.hasErrors()) {

            return "member/join";
        }else{
            //성공 로직
            userService.userSave(user);
            return "member/joinSuccess";
        }

    }


}
