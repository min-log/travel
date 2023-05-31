package com.example.travel.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class UserTravelDTO extends User {
    private String userId;
    private String password;
    private Boolean userSocial;

    private String userName;
    private Map<String,Object> attr; //OAuth2User 정보

    public UserTravelDTO(String username,
                         String password,
                         Boolean userSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = username;
        this.userSocial = userSocial;
    }

    public UserTravelDTO(String username,
                         String password,
                         Boolean userSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String,Object> attr) {
        this(username,password,userSocial,authorities); // 기존생성자
        this.password = password;
        this.attr = attr; //OAuth2User 정보
    }


    //OAuth2User 정보

}
