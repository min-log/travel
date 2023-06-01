package com.example.travel.security.service;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.repository.UserRepository;
import com.example.travel.security.dto.UserTravelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserTravelOAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        if (clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        // 2. 회원가입 로직
        UserTravel member = saveSocialUser(email);

        // DTO로 변경 회원정보 출력
        UserTravelDTO userTravelDTO = new UserTravelDTO(
                member.getUserEmail(),
                passwordEncoder.encode(member.getPassword()),
                true,
                member.getRoleSet().stream().map(
                        i -> new SimpleGrantedAuthority("ROLE_"+i.name())
                ).collect(Collectors.toSet()),
                oAuth2User.getAttributes()
        );
        userTravelDTO.setName(member.getName());

        return userTravelDTO;
    }

    private UserTravel saveSocialUser(String email) {
        Optional<UserTravel> userResult = userRepository.getUserByUserIdAndUserSocial(email, true);
        //존재하는 회원
        if (userResult.isPresent()){
            return userResult.get();
        }

        //존재 하지 않는 회원
        UserTravel userSave = UserTravel.builder().userEmail(email)
                .userId(email)
                .password("1111") //기본 비밀번호
                .userSocial(true)
                .build();
        userSave.roleAdd(UserRole.USER); // 권한
        UserTravel save = userRepository.save(userSave); //저장 

        return save;
    }
}
