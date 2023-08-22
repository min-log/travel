package com.example.travel.controller.member;


import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.dto.user.UserResponseDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.user.MailSendService;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailSendService mailSendService;

    //==========================================================
    //회원가입
    @GetMapping("/join")
    public String userJoinForm(Model model){

        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        log.info("join page ----------------------");
        return "member/join";
    }


    @PostMapping("/join")
    public String userJoin(@Valid @ModelAttribute("userTravel") UserDTO user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
    ) throws Exception, IOException {

            if(bindingResult.hasErrors()) {
                log.info("회원 가입 실패");
                return "/member/join";
            }else{
                //성공 로직
                System.out.println(user.getUserImg());
                System.out.println("-------------------------------");
                UserTravel userTravel1 = userService.userSave(user);

                log.info("회원가입 성공 :{}" , userTravel1);


                String userEmail = user.getUserEmail();
                mailSendService.sendEmail(userEmail, userTravel1.getName(),"joinSuccess");
                redirectAttributes.addFlashAttribute("joinSuccess","회원가입이 완료되었습니다.");
                return "redirect:/member/loginForm";
            }

    }



    //==========================================================
    //로그인 페이지
    @GetMapping("/loginForm")
    public String login(
                        @RequestParam(value = "error", required = false) String error, // 로그인 실패시 전달되는 파라미터
                        @RequestParam(value = "exception", required = false) String exception, // 로그인 실패시 전달되는 파라미터
                        //@RequestParam(value = "joinSuccess", required = false) String joinSuccess, // 회원가입 성송 시 전달되는 파라미터
                        String logout,
                        Model model,
                        HttpServletRequest request,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes){

        if (authentication != null){ //로그인 상태일 시 페이지 이동 막기
            redirectAttributes.addFlashAttribute("login", "on");
            return "redirect:/main";
        }

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("cng",flashMap.get("cng"));
            model.addAttribute("joinSuccess", flashMap.get("joinSuccess"));
        }


        model.addAttribute("userTravel",new UserDTO());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);


        return "member/login";
    }


    //==========================================================
    // 아이디 찾기
    @GetMapping("/userId")
    public String userIdSearch(Model model){
        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요
        return "member/userId";
    }
    @PostMapping("/userIdCheck")
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


    @GetMapping("/userPassword")
    public String userPasswordSearch(Model model,HttpServletRequest request){
        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("errTit",flashMap.get("errTit"));
            model.addAttribute("errCont", flashMap.get("errCont"));
        }

        return "member/userPassword";
    }

    @PostMapping("/userPasswordModify")
    public String userPasswordCheck(@ModelAttribute("userTravel") UserDTO user ,Model model,
            HttpServletRequest request,
            RedirectAttributes redirect){
        log.info("회원 비밀번호 찾기  =======================");

        log.info("user.getUserId() : {}",user.getUserId());
        log.info("user.getName() : {}",user.getName());
        log.info("user.getUserEmail() : {}",user.getUserEmail());


        UserDTO result = userService.userGetPassword(user.getUserId(), user.getName(), user.getUserEmail());
        log.info("result :{}" ,result);

        if(result == null){
            redirect.addFlashAttribute("errTit","회원확인");
            redirect.addFlashAttribute("errCont","일치하는 회원이 없습니다.");
            log.info("일치하는 회원이 없습니다.");

            return "redirect:/member/userPassword";
        }


        return "member/userPasswordModify";
    }

    //비밀번호 변경
    @PostMapping("/userModify")
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

            redirectAttributes.addFlashAttribute("cng","비밀번호가 변경되었습니다.");
            return "redirect:/member/loginForm";
        }


    }











}
