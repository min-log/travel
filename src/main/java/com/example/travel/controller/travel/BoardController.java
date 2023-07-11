package com.example.travel.controller.travel;

import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.service.travel.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    final CategoryService categoryService;
    @GetMapping("/boardList")
    public String categoryList(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "order",required = false) String order,
            HttpSession httpSession,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";

        Page<CategoryDTO> categoryPage = categoryService.getCategoryList(page ,order);
        PageingDTO pageingDTO = new PageingDTO(categoryPage);
        log.info("pageingDTO.getPage() : {} ",pageingDTO.getPage());

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);

        return "/travel/boardList";
    }


}
