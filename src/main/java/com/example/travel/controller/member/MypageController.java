package com.example.travel.controller.member;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.LikeCategoryDTO;
import com.example.travel.dto.travel.PageingDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserService  userService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String myPage(HttpServletRequest request,
                         Model model,
                         HttpSession session){


        UserTravelDTO userT = (UserTravelDTO) session.getAttribute("userT");
        Page<CategoryDTO> categoryPage = categoryService.getCategoryMYPage(userT.getUserNo(), 1 ,"dateStart",2);
        Page<CategoryDTO> categoryLikePage = categoryService.getCategoryLikeMYPage(userT.getUserNo(), 1 ,"dateStart",6);

        PageingDTO pageingDTO = new PageingDTO(categoryPage);

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("categoryLikePage",categoryLikePage);



        log.info("마이페이지");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("msg",flashMap.get("msg"));
        }

        return "mypage/main";
    }


    @GetMapping("/userImageModify")
    public String userImageModify(
                Model model,
                @AuthenticationPrincipal UserTravelAdapter user){

        String userId = user.getUserId();
        log.info(userId);
        UserTravel userValue = UserTravel.builder()
                .userId(userId)
                .build();

        model.addAttribute("userTravel",userValue); // 빈 객체 전달 필요

        return "mypage/userImageModify";
    }

    @PostMapping("/userImageModify")
    public String userImageModifyForm(@ModelAttribute("userTravel") UserDTO userDto,
                                      @AuthenticationPrincipal UserTravelAdapter user,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes){
        log.info("프로필 이미지 변경==============================");
        log.info(userDto.getUserId());
        log.info(userDto.getUserImg().getName());
        log.info(userDto.getUserImg());
        String profileImage = userService.userProfileImage(userDto);
        log.info(profileImage);


        if (profileImage != null){
            redirectAttributes.addFlashAttribute("modal","이미지가 변경되었습니다.");
            log.info("변경된 이미지 명 : {}",profileImage);
            user.setProfile(profileImage);
            user.setPath("true");
            session.setAttribute("userT",user);

            return "redirect:/mypage";
        }else{
            redirectAttributes.addFlashAttribute("modal","이미지 변경이 실패 했습니다.");
            return "redirect:mypage/userImageModify";
        }
    }

    @GetMapping("/userModify")
    public String userModify(
            Model model,
            @AuthenticationPrincipal UserTravelAdapter user){

        log.info("회원정보 수정페이지 이동");
        String userId = user.getUserId();
        log.info(userId);
        UserDTO userDto = UserDTO.builder().userId(userId).build();
        UserTravel userTravel = userService.userInfo(userDto);

        UserDTO userDTO = userService.entityToDto(userTravel);
        log.info(userDTO);
        model.addAttribute("userTravel",userDTO); // 빈 객체 전달 필요

        return "mypage/userModify";
    }

    @PostMapping("/userModifyForm")
    public String userModify(@Valid @ModelAttribute("userTravel") UserDTO user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        log.info("회원정보 수정 로직====================");
        log.info(user);

        boolean result = userService.userModitfy(user);
        log.info(result);
        if (result){
            log.info("성공");
            redirectAttributes.addFlashAttribute("msg","회원정보가 수정되었습니다.");
            return "redirect:/mypage";
        }else{
            log.info("실패");
            redirectAttributes.addFlashAttribute("msg","회원정보가 수정이 실패했습니다.");
            return "redirect:/mypage/userModify";
        }

    }


    //회원탈퇴
    @GetMapping("/withdrawal")
    public String withdrawal(
            @AuthenticationPrincipal UserTravelAdapter user,
            Model model,
             HttpServletRequest request){
        log.info("회원탈퇴 페이지 이동 ----------------");
        UserTravel userValue = UserTravel.builder().userId(user.getUserId()).build();
        model.addAttribute("userTravel",userValue); // 빈 객체 전달 필요

        return "mypage/withdrawal";
    }



    @Transactional
    @PostMapping("/withdrawalForm")
    public String withdrawalForm(@ModelAttribute("userTravel") UserDTO user,
                                 RedirectAttributes redirectAttributes){
        log.info("회원탈퇴 ------------------------------");
        boolean result = userService.userDelete(user.getUserId(), user.getPassword());
        log.info("회원제거 결과 : {}",result);
        if (result){
            redirectAttributes.addFlashAttribute("msg","회원탈퇴가 되었습니다.");
            log.info("성공");
            return "redirect:/logout";
        }else{
            log.info("실패");
            redirectAttributes.addFlashAttribute("msg","회원탈퇴가 실패했습니다. 비밀번호를 확인해주세요.");
            return "redirect:/mypage/withdrawal";
        }
    }

    //내 여행 리스트
    @GetMapping("/boardList")
    public String myBoardList(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "order",required = false) String order,
            HttpSession session,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";

        UserTravelDTO userT = (UserTravelDTO) session.getAttribute("userT");
        Page<CategoryDTO> categoryPage = categoryService.getCategoryMYPage(userT.getUserNo(), page ,order,6);
        PageingDTO pageingDTO = new PageingDTO(categoryPage);



        log.info("categoryPage : {}",categoryPage);
        log.info("pageingDTO {}",pageingDTO);

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);
        return "/mypage/boardList";
    }




    @GetMapping("/boardInvitedList")
    public String myBoardInvitedList(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "order",required = false) String order,
            HttpSession session,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";

        UserTravelDTO userT = (UserTravelDTO) session.getAttribute("userT");
        Page<CategoryDTO> categoryPage = categoryService.getCategoryInvitedMYPage(userT.getUserId(), page ,order);

        PageingDTO pageingDTO = new PageingDTO(categoryPage);

        log.info("categoryPage : {}",categoryPage);
        log.info("pageingDTO {}",pageingDTO);

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);
        return "/mypage/boardInvitedList";
    }




    @GetMapping("/boardLikeList")
    public String myBoardLikeList(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "order",required = false) String order,
            HttpSession session,
            Model model){

        if(page == null) page= 1;
        if(order == null) order= "dateStart";

        UserTravelDTO userT = (UserTravelDTO) session.getAttribute("userT");
        Page<CategoryDTO> categoryPage = categoryService.getCategoryLikeMYPage(userT.getUserNo(), page ,order,6);

        PageingDTO pageingDTO = new PageingDTO(categoryPage);

        log.info("categoryPage : {}",categoryPage);
        log.info("pageingDTO {}",pageingDTO);

        model.addAttribute("categoryPage",categoryPage);
        model.addAttribute("pageing",pageingDTO);
        model.addAttribute("orderCk",order);
        return "/mypage/boardLikeList";
    }






}
