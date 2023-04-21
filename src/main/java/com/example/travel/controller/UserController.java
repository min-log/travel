package com.example.travel.controller;


import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class UserController {
    private UserService userService;

    @GetMapping
    public String userJoinForm(){
        return "member/join";
    }

    @PostMapping
    public String userJoin(UserDTO userDTO){

        userService.userSave(userDTO);

        return "member/joinOk";
    }


}
