package com.example.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class MainController {
    @GetMapping("/")
    public String main(){
        return "pages/index";
    }
    @PostMapping("/")
    public void mainList(){


    }
}
