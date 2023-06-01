package com.example.travel.security.service;

import com.example.travel.domain.UserTravel;
import com.example.travel.repository.UserRepository;
import com.example.travel.security.dto.UserTravelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserTravelDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("확인 중 ");
        Optional<UserTravel> userResult = userRepository.getUserByUserIdAndUserSocial(username, false);
        UserTravel userTravel = userResult.get();
        log.info("user : {}",userTravel.getUserId());
        if (!userResult.isPresent()){
            log.info("실패");
            throw new UsernameNotFoundException("Check Email or Social");
        }

        log.info("2user : {}",userTravel.getPassword());
        // 있을 경우 UserTravelDTO로 변경
        UserTravelDTO userTravelDTO = new UserTravelDTO(
                userTravel.getUserId(),
                userTravel.getPassword(),
                userTravel.getUserSocial(),
                userTravel.getRoleSet().stream().map
                ( i->
                    new SimpleGrantedAuthority("ROLE_"+i.name())).collect(Collectors.toSet())
        );


        return userTravelDTO;
    }
}
