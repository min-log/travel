package com.example.travel.security.service;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.repository.member.UserImageRepository;
import com.example.travel.repository.member.UserRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserTravelOAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 관련 추가
    private final UserImageRepository userImageRepository;


    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("social login =============================");
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName : {}",clientName);
        log.info("clientName : {}",userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k+":"+v);
        });

        //회원가입

        // 1. 이메일 추출
        String email = null;
        String picture = null;
        String name = null;
        String mobile = null;

        if (clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
            picture =oAuth2User.getAttribute("picture");


        }else if(clientName.equals("Naver")){
            log.info("네이버");
            Map map = (Map) oAuth2User.getAttributes().get("response");
            email =(String)map.get("email");
            picture =(String)map.get("profile_image");
            name = (String)map.get("name");
            mobile = (String)map.get("mobile");
        }else if(clientName.equals("Kakao")){
            log.info("카카오");
            Map kakaoAccount = (Map)oAuth2User.getAttributes().get("kakao_account");
            Map profile = (Map) kakaoAccount.get("profile");

            email =(String)kakaoAccount.get("email");
            picture =(String)profile.get("profile_image_url");
            name = (String)profile.get("nickname");
        }


        log.info("email : {}",email);
        log.info("picture : {}",picture);

        Optional<UserTravel> userByUserIdAndUserSocial = userRepository.getUserByUserIdAndUserSocial(email, true);

        if (userByUserIdAndUserSocial.isPresent()){
            log.info("OAuth 유저가 있을 경우");

            Optional<UserTravel> User = userRepository.getUserPullByUserId(userByUserIdAndUserSocial.get().getUserId());
            UserTravelAdapter userTravelAdapter = new UserTravelAdapter(User.get(), oAuth2User.getAttributes());
            return userTravelAdapter;

        }


        // 2. 회원가입 로직
        log.info("userOAuth Google 회원가입 로직 ===============");
        UserTravel userOAuth = UserTravel.builder()
                .userEmail(email)
                .userId(email)
                .name(name)
                .userPhone(mobile)
                .password(passwordEncoder.encode("1111"))
                .userSocial(true)
                .build();
        userOAuth.roleAdd(UserRole.USER); // 권한 추가

        UserImage build = UserImage.builder().build(); //저장할 이미지
        if (picture != null){ // 이미지가 있을경우
            log.info("이미지가 존재");
            build.setOriginFileName(picture);
        }else{
            log.info("이미지가 없음");
        }


        UserImage ImageSaveResult = userImageRepository.save(build); // 이미지 저장
        userOAuth.updateUserImage(ImageSaveResult);
        UserTravel save = userRepository.save(userOAuth);  // 회원 저장
        UserTravelAdapter userTravelAdapter = new UserTravelAdapter(save, oAuth2User.getAttributes());

        return userTravelAdapter;

    }


}
