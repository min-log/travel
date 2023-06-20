package com.example.travel.controller.api;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    private final UserService userService;
    final PasswordEncoder passwordEncoder;
    @Transactional
    @GetMapping("/userPassword")
    public Boolean userPassword(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw
    ){
        log.info("id"+ id);
        log.info("pw"+ pw);
        UserDTO build = UserDTO.builder().userId(id).build();
        UserTravel userTravel = userService.userInfo(build);

        String pwEncode = userTravel.getPassword();
        boolean matches = passwordEncoder.matches(pw,pwEncode);
        log.info(matches);

        return matches;
    }

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
