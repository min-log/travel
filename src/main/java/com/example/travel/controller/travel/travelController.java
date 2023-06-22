package com.example.travel.controller.travel;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/travel")
public class travelController {

    @GetMapping("")
    public String main(){
        return "travel/travel";
    }

    @GetMapping("/map")
    public String travelMap(){
        return "travel/travelMap";
    }



}
