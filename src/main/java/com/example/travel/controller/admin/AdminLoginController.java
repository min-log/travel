package com.example.travel.controller.admin;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.LikeCategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.travel.CategoryBoardService;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.user.MailSendService;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
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
import java.util.List;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    private final UserService userService;
    private final MailSendService mailSendService;


    @GetMapping("")
    public String adminLogin(@RequestParam(value = "error", required = false) String error, // 로그인 실패시 전달되는 파라미터
                             @RequestParam(value = "exception", required = false) String exception, // 로그인 실패시 전달되는 파라미터
                             Model model,
                             HttpServletRequest request,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes){
        log.info("admin login page ----------------------");
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

        return "admin/login";
    }


    @GetMapping("/join")
    public String joinForm(Model model){
        log.info("join page ----------------------");
        model.addAttribute("userTravel",new UserDTO()); // 빈 객체 전달 필요

        return "admin/join";
    }

    @PostMapping("/join")
    public String joinFormSave(@Valid @ModelAttribute("userTravel") UserDTO user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws Exception, IOException {
        if(bindingResult.hasErrors()) {
            log.info("회원 가입 실패");
            log.info(bindingResult.hasErrors());
            return "admin/join";
        }else{
            //성공 로직
            System.out.println(user.getUserImg());
            System.out.println("-------------------------------");
            UserTravel userTravel1 = userService.userAdminSave(user);
            log.info("회원가입 성공 :{}" , userTravel1);
            String userEmail = user.getUserEmail();
            mailSendService.sendEmail(userEmail, userTravel1.getName(),"joinSuccess");
            redirectAttributes.addFlashAttribute("joinSuccess","회원가입이 완료되었습니다.");
            return "redirect:/admin";
        }



    }







}
