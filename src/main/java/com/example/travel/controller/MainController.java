package com.example.travel.controller;

import com.example.travel.domain.UserTravel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping(value = {"","main"})
    public String main(){

        return "pages/index";
    }
}
