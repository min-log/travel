package com.example.travel.security.dto;

import com.example.travel.domain.UserTravel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
@Log4j2
public class UserTravelAdapter extends UserTravelDTO implements Serializable {
    private UserTravel userTravel;
    public UserTravelAdapter(
            UserTravel userTravel
    ) {
        super(userTravel.getUserId(),
                userTravel.getPassword(),
                userTravel.getUserSocial(),
                userTravel.getUserNo(),
                userTravel.getUserImg().getOriginFileName(),
                userTravel.getRoleSet().stream().map( i-> new SimpleGrantedAuthority("ROLE_"+i.name())).collect(Collectors.toSet()));
        this.userTravel = userTravel;
    }

    public UserTravelAdapter(UserTravel userTravel,
                             Map<String, Object> attr) {
        super(userTravel.getUserId(),
                userTravel.getPassword(),
                userTravel.getUserSocial(),
                userTravel.getUserNo(),
                userTravel.getUserImg().getOriginFileName(),
                userTravel.getRoleSet().stream().map( i-> new SimpleGrantedAuthority("ROLE_"+i.name())).collect(Collectors.toSet())
                , attr);
    }
}
