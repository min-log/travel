package com.example.travel.controller.travel;


import com.example.travel.dto.travel.CategoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Log4j2
@Controller
@RequestMapping("/travel")
public class travelController {

    @GetMapping("")
    public String main(Model model){
        model.addAttribute("category",new CategoryDTO());
        return "travel/travel";
    }
    @PostMapping("/category")
    public String categorySave(@ModelAttribute(value = "category") CategoryDTO categoryDTO,
                               RedirectAttributes redirectAttributes) {

        log.info(" 카테고리 저장 --------------------");
        log.info(categoryDTO);

        return "travel/travelMap";

    }

    @GetMapping("/map")
    public String travelMapSave(){
        return "travel/travelMap";
    }




}
