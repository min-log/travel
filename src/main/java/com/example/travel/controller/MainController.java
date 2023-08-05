package com.example.travel.controller;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.dto.travel.RankingDTO;
import com.example.travel.repository.member.UserImageRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.FileService;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

    final FileService service;
    final UserImageRepository userImageRepository;
    final CategoryService categoryService;
    final RankingService rankingService;


    @Transactional
    @GetMapping("security-login")
    public String securityLogin(@AuthenticationPrincipal UserTravelAdapter user,
                                HttpSession session) {
        log.info("회원 로그인 여부에 따른 세션");
        if (user != null){
            log.info("로그인"); String profile = user.getProfile();
            String img=null;
            try{
                    Optional<UserImage> byOriginFileName = userImageRepository.findByOriginFileName(profile);
                    if (byOriginFileName.isPresent()){
                        UserImage userImage = byOriginFileName.get();
                        String originFileName = userImage.getOriginFileName();
                        img = originFileName;
                        log.info(img);
                        user.setProfile(img);
                        log.info(user.getPath());
                    }else {
                        log.info("이미지 없음");
                    }
            }catch (Exception e){
                log.info("동일한 이미지 있음,소셜 기본 이미지 일때");
                e.printStackTrace();
            }
            session.setAttribute("userT",user);
        }else{
            log.info("로그아웃");
            session.removeAttribute("userT");
        }
        return "redirect:/main";
    }

    @GetMapping(value = {"","main"})
    public String main(
            HttpServletRequest request,
            Model model,
            HttpSession session){


        Page<RankingDTO> rankingDTOS = rankingService.rankingList();
        model.addAttribute("rankingList",rankingDTOS);

        Page<CategoryDTO> categoryPageImg = categoryService.getCategoryImgList(6,1,"viewNum");
        model.addAttribute("categoryPageImg",categoryPageImg);


        Page<CategoryDTO> categoryPagePopularity = categoryService.getCategoryPopularityList(15,1,"viewNum");
        model.addAttribute("categoryPagePopularity",categoryPagePopularity);


        Page<CategoryDTO> categoryPageNew = categoryService.getCategoryList(4,1 ,"createdAt","");
        model.addAttribute("categoryPageNew",categoryPageNew);



        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }


        return "pages/index";
    }



}
