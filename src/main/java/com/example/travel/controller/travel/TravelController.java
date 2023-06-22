package com.example.travel.controller.travel;


import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/travel")
public class TravelController {

    @GetMapping("")
    public String main(Model model, @AuthenticationPrincipal UserTravelAdapter user){

        Long userNo = user.getUserNo();
        CategoryDTO categoryDTO = CategoryDTO.builder().userTravelNo(userNo).build();
        model.addAttribute("category",categoryDTO);

        return "travel/travel";
    }
    @PostMapping("/category")
    public String categorySave(@ModelAttribute("category") CategoryDTO categoryDTO) {

        log.info(" 카테고리 저장 --------------------");
        log.info(categoryDTO);

        return "travel/travelMap";

    }

    @GetMapping("/map")
    public String travelMapSave(){
        return "travel/travelMap";
    }




}
