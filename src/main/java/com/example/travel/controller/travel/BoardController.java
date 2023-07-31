package com.example.travel.controller.travel;

import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.LikeCategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.security.dto.UserTravelDTO;
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
import java.util.List;

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
            @RequestParam(value = "k",required = false) String keyword,
            HttpSession httpSession,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";
        if (keyword == null) keyword = "";

        Page<CategoryDTO> categoryPage = categoryService.getCategoryList(page ,order,keyword);


        PageingDTO pageingDTO = new PageingDTO(categoryPage);
        log.info("pageingDTO.getPage() : {} ",pageingDTO.getPage());

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);

        UserTravelDTO userT = (UserTravelDTO)httpSession.getAttribute("userT");
        if (userT != null){ //유저가 있을때만
            List<LikeCategoryDTO> likeList = categoryService.categoryLikeList(userT.getUserNo());
            model.addAttribute("likeList",likeList);
        }




        return "/board/boardList";
    }


}
