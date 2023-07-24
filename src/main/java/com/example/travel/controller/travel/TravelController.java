package com.example.travel.controller.travel;

import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.DayInfoDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.travel.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
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
    public String main(Model model,
                       @AuthenticationPrincipal UserTravelAdapter user,
                       @RequestParam(value = "no", required = false) Long no,
                       HttpServletRequest request
                       ){

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }


        Long userNo = user.getUserNo();
        CategoryDTO categoryDTO = CategoryDTO.builder().userTravelNo(userNo).build();
        if (no != null){
            categoryDTO.setCategoryNo(no);
            CategoryDTO category = categoryService.getCategory(no);
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryDTO.setCategoryOpen(category.isCategoryOpen());
            categoryDTO.setDateStart(category.getDateStart());
            categoryDTO.setDateEnd(category.getDateEnd());
            categoryDTO.setCategoryArea(category.getCategoryArea());
            categoryDTO.setCategoryAreaDetails(category.getCategoryAreaDetails());
            categoryDTO.setTags(category.getTags());
            categoryDTO.setTagList(category.getTagList());

        }


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
            Model model,
            Authentication authentication,
            RedirectAttributes redirectAttributes
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


        // 해당 유저의 글이 아닐 시 페이지 접근 막기
        boolean userCk = userCk(authentication, categoryDTO);
        if (userCk == false){
            redirectAttributes.addFlashAttribute("msg","접속 할 수 없는 페이지 입니다.");
            return "redirect:/board/boardList";
        }

        return "travel/travelMap";
    }

    @GetMapping("/categoryDelete")
    public String categoryDelete(@RequestParam("categoryNo") Long no,RedirectAttributes redirectAttributes){
        categoryService.categoryDelete(no);
        redirectAttributes.addFlashAttribute("msg","게시물이 삭제되었습니다.");
        return "redirect:/mypage/boardList";
    }

    @GetMapping("/view")
    public String categoryVeiw(
            @RequestParam(value = "no") long no,
            Model model,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ){
        log.info("상세페이지");
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



        // 비공개 글의 경우 해당 유저의 글이 아닐 시 페이지 접근 막기
        boolean categoryOpen = categoryDTO.isCategoryOpen();
        if (categoryOpen == false){
            boolean userCk = userCk(authentication, categoryDTO);
            if (userCk == false){
                redirectAttributes.addFlashAttribute("msg","접속 할 수 없는 페이지 입니다.");
                return "redirect:/board/boardList";
            }
        }

        return "travel/travelView";
    }


    // 해당 유저의 글이 아닐 시 페이지 접근 막기
    public boolean userCk(Authentication authentication,CategoryDTO categoryDTO){
        String name = authentication.getName();
        String[] tagList = categoryDTO.getTagList();
        String categoryWriter = categoryDTO.getCategoryWriter();
        boolean userCk = false;
        if (tagList != null) {
            for (int i = 0; i < tagList.length; i++) {
                if (tagList[i].equals(name)) {
                    userCk = true;
                }
            }
        }
        if (categoryWriter.equals(name)) userCk = true;
        log.info(userCk);
        return userCk;
    }


    //---------------- 게시글 후기 작성
    @GetMapping("/post")
    public String postWriter(@RequestParam(value = "no") long no,
                             Model model
    ){
        log.info("게시글 후긴");
        CategoryDTO categoryDTO = categoryService.getCategory(no);
        DayInfoDTO days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        LocalDate localDate = LocalDate.parse(categoryDTO.getDateStart());
        int dayOfMonth = localDate.getDayOfMonth();

        ItemDTO item = ItemDTO.builder().categoryId(no).build();

        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",days);
        model.addAttribute("startDay",dayOfMonth);
        model.addAttribute("item",item);

        return "travel/travelPostWrite";
    }




}
