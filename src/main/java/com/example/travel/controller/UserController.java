package com.example.travel.controller;


import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.MailSendService;
import com.example.travel.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //==========================================================
    //회원가입
    @GetMapping("join")
    public String userJoinForm(Model model){

        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        log.info("join page ----------------------");
        return "member/join";
    }


    @PostMapping("join")
    public String userJoin(@Valid @ModelAttribute("userTravel") UserDTO user ,
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
                UserTravel userTravel1 = userService.userSave(user);
                log.info("회원가입 성공 :{}" , userTravel1);

                return "member/login";
            }
        }
        return "member/join";
    }



    //로그인 페이지
    @GetMapping("loginForm")
    public String login(String error, String logout, Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }


    // 아이디 찾기
    @GetMapping("member/userId")
    public String userIdSearch(Model model){

        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        return "member/userId";
    }
    @PostMapping("member/userIdCheck")
    public String userIdCheck(@ModelAttribute("userTravel") UserDTO user ,Model model){

        String Result = userService.userGetName(user.getName(), user.getUserEmail());
        log.info("Result = {}",Result);

        if(Result == null){
            model.addAttribute("errTit","회원확인");
            model.addAttribute("errCont","일치하는 회원이 없습니다.");
            log.info("일치하는 회원이 없습니다.");
            return "member/userId";
        }

        log.info("=======================");
        user.setUserId(Result);


        return "member/userIdCheck";

    }


    @GetMapping("member/userPassword")
    public String userPasswordSearch(){
        return "member/userPassword";
    }











}
