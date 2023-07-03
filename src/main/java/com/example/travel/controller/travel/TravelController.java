package com.example.travel.controller.travel;



import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.travel.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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


    @PostMapping("/category")
    public String categorySave(@ModelAttribute("category") CategoryDTO categoryDTO,
                               RedirectAttributes redirectAttributes) {

        log.info(" 아이템 생성 페이지 --------------------");

        //log.info(tags);
        CategoryDTO result = categoryService.categorySave(categoryDTO);
        if (result == null){
            redirectAttributes.addFlashAttribute("msg","임시저장은 5개 까지 가능합니다.");
            return "redirect:/travel";
        }
        redirectAttributes.addAttribute("no",result.getCategoryNo());
        return "redirect:/travel/category";
    }

    @GetMapping("/category")
    public String categoryMap(
            @RequestParam(value = "no") long no,
            Model model
    ){
        log.info(no);
        CategoryDTO categoryDTO = categoryService.getCategory(no);

        DayInfoDTO days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        log.info("days {}" , days.getDay());
        log.info("days info {}" , days.getDayInfo());
        LocalDate localDate = LocalDate.parse(categoryDTO.getDateStart());
        int dayOfMonth = localDate.getDayOfMonth();

        ItemDTO item = ItemDTO.builder().categoryId(no).build();

        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",days);
        model.addAttribute("startDay",dayOfMonth);
        model.addAttribute("item",item);

        return "travel/travelMap";
    }






    @GetMapping("/view")
    public String categoryVeiw(
            @RequestParam(value = "no") long no,
            Model model
    ){
        log.info(no);
        CategoryDTO categoryDTO = categoryService.getCategory(no);

        DayInfoDTO days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        log.info("days {}" , days.getDay());
        log.info("days info {}" , days.getDayInfo());
        LocalDate localDate = LocalDate.parse(categoryDTO.getDateStart());
        int dayOfMonth = localDate.getDayOfMonth();

        ItemDTO item = ItemDTO.builder().categoryId(no).build();

        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",days);
        model.addAttribute("startDay",dayOfMonth);
        model.addAttribute("item",item);

        return "travel/travelView";
    }




}
