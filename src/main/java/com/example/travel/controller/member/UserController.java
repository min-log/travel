package com.example.travel.controller.member;


import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.MailSendService;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;


@Log4j2
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailSendService mailSendService;


    //==========================================================
    //회원가입
    @GetMapping("join")
    public String userJoinForm(Model model){

        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        log.info("join page ----------------------");
        return "member/join";
    }


    @PostMapping("join")
    public String userJoin(@Valid @ModelAttribute("userTravel") UserDTO user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
    ) throws Exception, IOException {

//        int idCkNo = userService.userGetId(user.getUserId());
//        log.info("아이디 유무 확인 : {}",idCkNo);
//
//        user.setIdCk(idCkNo);
//
//        log.info("---------user :{}" , user);
//        if (idCkNo != 5){
            if(bindingResult.hasErrors()) {
                log.info("회원 가입 실패");
                log.info(bindingResult.hasErrors());
                return "member/join";
            }else{
                //성공 로직
                System.out.println(user.getUserImg());
                System.out.println("-------------------------------");
                UserTravel userTravel1 = userService.userSave(user);

                log.info("회원가입 성공 :{}" , userTravel1);


                String userEmail = user.getUserEmail();
                mailSendService.sendEmail(userEmail, userTravel1.getName(),"joinSuccess");
                redirectAttributes.addAttribute("joinSuccess","회원가입이 완료되었습니다.");
                return "redirect:/loginForm";
            }
//        }
//
//        return "member/join";
    }



    //==========================================================
    //로그인 페이지
    @GetMapping("loginForm")
    public String login(@RequestParam(value = "error", required = false) String error, // 로그인 실패시 전달되는 파라미터
                        @RequestParam(value = "exception", required = false) String exception, // 로그인 실패시 전달되는 파라미터
                        @RequestParam(value = "cng", required = false) String cng, // 비밀번호 변경시 전달되는 파라미터
                        @RequestParam(value = "joinSuccess", required = false) String joinSuccess, // 회원가입 성송 시 전달되는 파라미터
                        String logout,
                        Model model){

        model.addAttribute("userTravel",new UserDTO());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("cng", cng);
        model.addAttribute("joinSuccess", joinSuccess);

        return "member/login";
    }


    //==========================================================
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
    public String userPasswordSearch(Model model){
        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        return "member/userPassword";
    }

    @PostMapping("member/userPasswordModify")
    public String userPasswordCheck(@ModelAttribute("userTravel") UserDTO user ,Model model){
        log.info("회원 비밀번호 찾기  =======================");
        log.info("user.getUserNo() : {}",user.getUserNo());
        log.info("user.getUserId() : {}",user.getUserId());
        log.info("user.getName() : {}",user.getName());
        log.info("user.getUserEmail() : {}",user.getUserEmail());

        UserDTO result = userService.userGetPassword(user.getUserId(), user.getName(), user.getUserEmail());
        user.setUserNo(result.getUserNo());

        if(result == null){
            model.addAttribute("errTit","회원확인");
            model.addAttribute("errCont","일치하는 회원이 없습니다.");
            log.info("일치하는 회원이 없습니다.");

            return "member/userPassword";
        }

        return "member/userPasswordModify";
    }

    //비밀번호 변경
    @PostMapping("member/userModify")
    public String userPasswordModify(@ModelAttribute("userTravel") UserDTO user ,
                                     Model model,
                                     RedirectAttributes redirectAttributes){
        log.info("회원정보 수정 ==================================");
        log.info("화면 정보 : "+ user);

        String userId = userService.userPasswordModify(user);
        log.info("수정 정보 :" + userId);

        if (userId.equals("null")){
            log.info("회원정보가 없습니다.");
            model.addAttribute("errTit","회원확인");
            model.addAttribute("errCont","일치하는 회원이 없습니다.");
            log.info("일치하는 회원이 없습니다.");

            return "member/userPasswordModify";
        }else{
            redirectAttributes.addAttribute("cng","비밀번호가 변경되었습니다.");
            return "redirect:/loginForm";
        }


    }











}
