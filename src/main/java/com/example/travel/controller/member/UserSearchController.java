package com.example.travel.controller.member;

import com.example.travel.repository.UserRepository;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserSearchController {
    private final UserService userService;
    @GetMapping("idCheck")
    public Boolean idCheck(@RequestParam("id") String id){
        int i = userService.userGetId(id);
        log.info(i);
        if (i > 0){
            return false;
        }else{
            return true;
        }

    }


    @GetMapping("emailCheck")
    public Boolean emailCheck(@RequestParam("userEmail") String userEmail){
        boolean result = userService.userGetEmail(userEmail);
        return result;

    }
}
