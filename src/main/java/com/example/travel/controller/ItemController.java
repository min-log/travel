package com.example.travel.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/travel")
public class ItemController {

    @GetMapping("")
    public String main(){
        return "pages/travel";
    }
    @PostMapping("")
    public void mainList(){


    }

}
