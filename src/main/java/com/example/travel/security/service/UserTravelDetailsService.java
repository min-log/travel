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
    private final UserImageRepository userImageRepository;


    @Transactional
    @Override
    public UserTravelAdapter loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("확인 중 ");

        Optional<UserTravel> userResult = userRepository.getUserByUserIdAndUserSocial(username, false);
        UserTravel userTravel = userResult.get();

        String userId = userTravel.getUserId();
        log.info("user : {}", userId);
        if (!userResult.isPresent()) {
            log.info("실패");
            throw new UsernameNotFoundException("Check Email or Social");
        }

        UserTravelAdapter userTravelAdapter = userS(userId);


        return userTravelAdapter;

    }


    private UserTravelAdapter userS(String userId) {
        Optional<UserTravel> result = userRepository.getUserPullByUserId(userId);
        if (result.isPresent()){
            System.out.println("?");
            UserTravel userTravel1 = result.get();
            System.out.println(userTravel1);
            System.out.println(userTravel1.getUserImg().getOriginFileName());
            return new UserTravelAdapter(userTravel1);
        }else{
            System.out.println("없음");
            return null;
        }
    }
}
