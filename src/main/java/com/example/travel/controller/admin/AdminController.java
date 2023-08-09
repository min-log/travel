package com.example.travel.controller.admin;

import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.dto.travel.RankingDTO;
import com.example.travel.dto.user.PageingUserDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.travel.CategoryBoardService;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.RankingService;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/m/")
public class AdminController {

    final UserService userService;
    final CategoryService categoryService;
    final CategoryBoardService categoryBoardService;
    final RankingService rankingService;

    @GetMapping("/main")
    public String adminMain(Model model){
        Page<RankingDTO> rankingDTOS = rankingService.rankingList();
        model.addAttribute("rankingList",rankingDTOS);
        List<String> genderG = userService.userGenderGraph();
        for(String i: genderG){
            log.info(i);
        }

        model.addAttribute("genderG",genderG);
        model.addAttribute("title","메인");
        return "admin/index";
    }



    @GetMapping("/BoardTravel")
    public String adminBoardList(
        @RequestParam(value = "page",required = false) Integer page,
        @RequestParam(value = "order",required = false) String order,
        @RequestParam(value = "k",required = false) String keyword,
        HttpSession httpSession,
        Model model){
            log.info("게시판 리스트 ----------------------");
            if(page == null) page= 1;
            if(order == null) order= "dateStart";
            if(keyword == null) keyword = "";

            Page<CategoryDTO> categoryPage = categoryService.getCategoryListAdmin(6,page ,order,keyword);
            PageingDTO pageingDTO = new PageingDTO(categoryPage);



            log.info("pageingDTO.getPage() : {} ",pageingDTO.getPage());
            model.addAttribute("categoryPage",categoryPage);
            model.addAttribute("pageing",pageingDTO);
            model.addAttribute("orderCk",order);
            model.addAttribute("title","여행 게시판");

            return "admin/boardList";
    }


    @GetMapping("/BoardUser")
    public String adminUserList(@RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "order",required = false) String order,
                                @RequestParam(value = "k",required = false) String keyword,
                                HttpSession httpSession,
                                Model model){
        log.info("회원 리스트 ----------------------");
        if(page == null) page= 1;
        if(order == null) order= "user_no";
        if(keyword == null) keyword = "";

        Page<UserDTO> userDTOS = userService.userListAdmin(6, page, order, keyword,0);
        PageingUserDTO pageingDTO = new PageingUserDTO(userDTOS);



        log.info("pageingDTO.getPage() : {} ",pageingDTO.getPage());
        model.addAttribute("categoryPage",userDTOS);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);
        model.addAttribute("title","회원 리스트");
        model.addAttribute("pagePath","BoardUser");


        return "admin/userList";
    }




    @GetMapping("/BoardAdmin")
    public String adminList(@RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "order",required = false) String order,
                                @RequestParam(value = "k",required = false) String keyword,
                                HttpSession httpSession,
                                Model model){
        log.info("회원 리스트 ----------------------");
        if(page == null) page= 1;
        if(order == null) order= "created_at";
        if(keyword == null) keyword = "";

        Page<UserDTO> userDTOS = userService.userListAdmin(6, page, order, keyword,2);
        PageingUserDTO pageingDTO = new PageingUserDTO(userDTOS);



        log.info("pageingDTO.getPage() : {} ",pageingDTO.getPage());
        model.addAttribute("categoryPage",userDTOS);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);
        model.addAttribute("title","관리자 리스트");
        model.addAttribute("pagePath","BoardAdmin");


        return "admin/userList";
    }




    @GetMapping("/categoryDelete")
    public String categoryDelete(
            Authentication authentication,
            @RequestParam("categoryNo") Long no,RedirectAttributes redirectAttributes){
        categoryService.categoryDelete(no);
        redirectAttributes.addFlashAttribute("msg","게시물이 삭제되었습니다.");
        return "redirect:/admin/m/BoardTravel";
    }

}
