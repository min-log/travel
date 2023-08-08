package com.example.travel.security.service;

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
        log.info("확인 중 ");

        Optional<UserTravel> userResult = userRepository.getUserByUserIdAndUserSocial(username, false);
        UserTravel userTravel = userResult.get();

        log.info("user : {}", userTravel.getUserId());
        if (!userResult.isPresent()) {
            log.info("실패");
            throw new UsernameNotFoundException("Check Email or Social");
        }
        return new UserTravelAdapter(userTravel);
    }


}
