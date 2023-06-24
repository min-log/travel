package com.example.travel.controller.travel;


import com.example.travel.domain.Category;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.travel.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/travel")
public class TravelController {
    final CategoryService categoryService;

    @GetMapping("")
    public String main(Model model, @AuthenticationPrincipal UserTravelAdapter user){

        Long userNo = user.getUserNo();
        CategoryDTO categoryDTO = CategoryDTO.builder().userTravelNo(userNo).build();
        model.addAttribute("category",categoryDTO);

        return "travel/travel";
    }
    @PostMapping("/category")
    public String categorySave(@ModelAttribute("category") CategoryDTO categoryDTO,Model model) {

        log.info(" 아이템 생성 페이지 --------------------");
        log.info(categoryDTO);

        int days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        int[] arr = new int[days];

        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",arr);


        return "travel/travelMap";
    }

    @GetMapping("/map")
    public String travelMapSave(){
        return "travel/travelMap";
    }




}
