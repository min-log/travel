package com.example.travel.security.service;

import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.dto.ImageDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.repository.UserImageRepository;
import com.example.travel.repository.UserRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserTravelOAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 관련 추가
    private final FileService fileService;
    private final UserImageRepository userImageRepository;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

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

        if (clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
            picture =oAuth2User.getAttribute("picture");
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
       log.info("userOAuth 회원가입 로직 ===============");
        UserTravel userOAuth = UserTravel.builder()
                .userId(email)
                .userEmail(email)
                .password("1111")
                .userSocial(true)
                .build();
        if (picture != null){ // 이미지가 있을경우
            UserImage build = UserImage.builder().originFileName(picture).build();
            UserImage ImageSaveResult = userImageRepository.save(build);
            userOAuth.updateUserImage(ImageSaveResult);
            UserTravel save = userRepository.save(userOAuth);
            UserTravelAdapter userTravelAdapter = new UserTravelAdapter(save, oAuth2User.getAttributes());
            userTravelAdapter.setProfile(ImageSaveResult.getOriginFileName());
            return userTravelAdapter;
        }else{
            UserTravel save = userRepository.save(userOAuth);
            UserTravelAdapter userTravelAdapter = new UserTravelAdapter(save, oAuth2User.getAttributes());
            return userTravelAdapter;
        }

    }


}
