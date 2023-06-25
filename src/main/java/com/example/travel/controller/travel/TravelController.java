package com.example.travel.controller.travel;


import com.example.travel.domain.Category;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.travel.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/travel")
public class TravelController {
    final CategoryService categoryService;

    @GetMapping("")
    public String main(Model model, @AuthenticationPrincipal UserTravelAdapter user,
                       HttpServletRequest request){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }

        Long userNo = user.getUserNo();
        CategoryDTO categoryDTO = CategoryDTO.builder().userTravelNo(userNo).build();
        model.addAttribute("category",categoryDTO);
        return "travel/travel";
    }

    @Transactional
    @PostMapping("/category")
    public String categorySave(@ModelAttribute("category") CategoryDTO categoryDTO, Model model,
                               RedirectAttributes redirectAttributes) {

        log.info(" 아이템 생성 페이지 --------------------");
        log.info(categoryDTO);
        LocalDate localDate = LocalDate.parse(categoryDTO.getDateStart());
        int dayOfMonth = localDate.getDayOfMonth();

        CategoryDTO result = categoryService.categorySave(categoryDTO);
        if (result == null){
            redirectAttributes.addFlashAttribute("msg","임시저장은 5개 까지 가능합니다.");
            return "redirect:/travel";
        }

        int days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        int[] arr = new int[days];

        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",arr);
        model.addAttribute("startDay",dayOfMonth); //시작 날짜


        return "travel/travelMap";
    }

    @GetMapping("/map")
    public String travelMapSave(){
        return "travel/travelMap";
    }




}
