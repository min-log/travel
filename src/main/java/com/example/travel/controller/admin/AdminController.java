package com.example.travel.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String adminLogin(){
        return "admin/login";
    }


    @GetMapping("/main")
    public String adminMain(){
        return "admin/index";
    }

}
