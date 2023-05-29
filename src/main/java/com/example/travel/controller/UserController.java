package com.example.travel.controller;


import com.example.travel.domain.User;
import com.example.travel.dto.user.UserSaveDTO;
import com.example.travel.dto.user.UserSaveResultDTO;
import com.example.travel.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    public String userJoinForm(Model model){

        model.addAttribute("user",new UserSaveDTO()); // 빈 객체 전달 필요
        log.info("join page ----------------------");
        return "member/join";
    }


    @PostMapping("/join")
    public String userJoin(@Valid @ModelAttribute("user") UserSaveDTO user ,
                           BindingResult bindingResult){

        int idCkNo = userService.userGetId(user.getUserId());
        log.info("아이디 유무 확인 : {}",idCkNo);
        user.setIdCk(idCkNo);

        log.info("---------user :{}" , user);
        if (idCkNo != 5){
            if(bindingResult.hasErrors()) {
                log.info("회원 가입 실패");
                return "member/join";
            }else{
                //성공 로직
                User user1 = userService.userSave(user);
                log.info("user1 :{}" , user1);
                return "member/joinSuccess";
            }
        }
        return "member/join";
    }


}
