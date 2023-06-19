package com.example.travel.controller.member;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.dto.user.UserResponseDTO;
import com.example.travel.repository.UserImageRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.security.service.UserTravelDetailsService;
import com.example.travel.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserService  userService;
    final UserImageRepository userImageRepository;
    @GetMapping("")
    public String myPage(@AuthenticationPrincipal UserTravelAdapter user,
                         HttpSession session,
                         HttpServletRequest request, Model model){
        log.info("마이페이지");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // redirect 에러메시지
        if(flashMap!=null) {
            model.addAttribute("login",flashMap.get("login"));
        }



        //log.info("회원 로그인 성공 여부에 따른 쿠키 제거");
        if (user != null){
            log.info("로그인"); String profile = user.getProfile();
            String img=null;

            Optional<UserImage> byOriginFileName = userImageRepository.findByOriginFileName(profile);
            if (byOriginFileName.isPresent()){
                log.info("있음");
                UserImage userImage = byOriginFileName.get();
                String path = userImage.getPath();
                String originFileName = userImage.getOriginFileName();
                img = "\\upload\\" +path +"\\"+ originFileName;
                user.setProfile(img);
            }else {
                log.info("없음");
            }
            session.setAttribute("userT",user);
        }else{
            log.info("로그아웃");
            session.removeAttribute("user");
        }




        return "mypage/main";
    }


    @GetMapping("/userImageModify")
    public String userImageModify(Model model,@AuthenticationPrincipal UserTravelAdapter user){
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
        String profileImage = userService.userProfileImage(userDto);
        log.info(profileImage);


        if (profileImage != null){
            redirectAttributes.addFlashAttribute("modal","이미지가 변경되었습니다.");
            log.info("변경된 이미지 명 : {}",profileImage);
            user.setProfile(profileImage);
            session.setAttribute("userT",user);
            return "redirect:/mypage";
        }else{
            redirectAttributes.addFlashAttribute("modal","이미지 변경이 실패 했습니다.");
            return "redirect:mypage/userImageModify";
        }
    }

    @GetMapping("/userModify")
    public String userModify(Model model,@AuthenticationPrincipal UserTravelAdapter user){
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

    @GetMapping("/userPassword")
    public String userPassword(
                                @AuthenticationPrincipal UserTravelAdapter user,
                                @ModelAttribute("userTravel") UserDTO userRe ,
                               Model model,
                               RedirectAttributes redirectAttributes){
        log.info("화면 정보 : "+ user);
        String userId = user.getUserId();
        log.info(userId);




            return "/mypage/userPassword";


    }


}
