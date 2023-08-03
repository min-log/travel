package com.example.travel.controller;

import com.example.travel.service.user.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailSendService mailSendService;
    //이메일 인증
    @GetMapping("/mailCheck")
    @ResponseBody
    public String mailCheck(@Param(value = "email") String email) throws Exception {
        System.out.println("이메일 인증 요청이 들어옴!");
        System.out.println("이메일 인증 이메일 : " + email);

        String userCheck = mailSendService.sendEmail(email, email,"userCheck");// 인증 번호
        log.info("이메일 인증 번호 : " + userCheck);
        return userCheck;
    }


}
