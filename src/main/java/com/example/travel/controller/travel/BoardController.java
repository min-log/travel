package com.example.travel.controller.travel;

import com.example.travel.dto.travel.*;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.travel.CategoryBoardService;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    final CategoryService categoryService;
    final CategoryBoardService categoryBoardService;
    final RankingService rankingService;

    @GetMapping("/boardList")
    public String categoryList(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "k",required = false) String keyword,
            HttpSession httpSession,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";
        if(keyword == null) keyword = "";


        Page<CategoryDTO> categoryPage = categoryService.getCategoryList(6,page ,order,keyword);

        PageingDTO pageingDTO = new PageingDTO(categoryPage);

        if(!keyword.equals("")) { //키워드 검색 시 , 내용이 존재한다면
            if(!pageingDTO.getPageList().isEmpty()){
                rankingService.rankingUp(keyword);
            }
        }




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




    @GetMapping("/view")
    public String categoryVeiw(
            Authentication authentication,
            @RequestParam(value = "no") long no,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        log.info("상세페이지");
        CategoryDTO categoryDTO = categoryService.getCategory(no);
        categoryService.categoryViewNumUpdate(no);// 조회수 증가
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


        return "board/travelView";
    }

    @GetMapping("/postView")
    public String postView(
            Authentication authentication,
            @RequestParam(value = "no") long no,
            @RequestParam(value = "day" , required = false) int dayNo,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        log.info("작성 게시물 상세 페이지 -------------");
        log.info(dayNo);

        CategoryDTO categoryDTO = categoryService.getCategory(no);
        categoryService.categoryViewNumUpdate(no);// 조회수 증가
        DayInfoDTO days = categoryService.categoryDays(categoryDTO.getDateStart(), categoryDTO.getDateEnd());
        LocalDate localDate = LocalDate.parse(categoryDTO.getDateStart());
        int dayOfMonth = localDate.getDayOfMonth();

        ItemDTO item = ItemDTO.builder().categoryId(no).build();
        CategoryBoardDTO categoryBoardDTO = CategoryBoardDTO.builder()
                .boardContent("기록이 없습니다.").build();

        CategoryBoardDTO categoryBoardGet = categoryBoardService.getCategoryBoard(no,dayNo); // 카테고리 가져오기
        if (categoryBoardGet != null){ // 카테고리가 있을경우
            categoryBoardDTO = categoryBoardGet;
            String txt = HtmlUtils.htmlUnescape(categoryBoardDTO.getBoardContent());
            categoryBoardDTO.setBoardContent(txt);
        }


        log.info("categoryBoardDTO : {}",categoryBoardDTO);
        model.addAttribute("board", categoryBoardDTO);
        model.addAttribute("category",categoryDTO);
        model.addAttribute("days",days);
        model.addAttribute("startDay",dayOfMonth);
        model.addAttribute("item",item);

        return "board/travelPostView";
    }






}
