package com.example.travel.controller;

import com.example.travel.dto.user.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping("/")
public class MainController {


    @GetMapping(value = {"","main"})
    public String main(@RequestParam(value = "login",required = false) Boolean login,
                       RedirectAttributes redirectAttributes){



        return "pages/index";
    }
}
