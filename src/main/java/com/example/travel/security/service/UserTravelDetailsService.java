package com.example.travel.security.service;

import com.example.travel.domain.UserRole;
import com.example.travel.domain.UserTravel;
import com.example.travel.repository.member.UserImageRepository;
import com.example.travel.repository.member.UserRepository;
import com.example.travel.security.dto.UserTravelAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserTravelDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    @Override
    public UserTravelAdapter loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 로직");

        Optional<UserTravel> userResult = userRepository.getUserByUserIdAndUserSocial(username, false);
        UserTravel userTravel = userResult.get();
        log.info("user : {}", userTravel.getUserId());
        if (!userResult.isPresent()) {
            log.info("실패");
            throw new UsernameNotFoundException("Check Email or Social");
        }
//        for (UserRole userRole : userTravel.getRoleSet()) {
//            log.info("userRole -> {}" ,userRole);
//            if (userRole.toString().equals("ADMIN")){
//                throw new UsernameNotFoundException("ROLE_ADMIN");
//            }
//        }


        return new UserTravelAdapter(userTravel);
    }


}
