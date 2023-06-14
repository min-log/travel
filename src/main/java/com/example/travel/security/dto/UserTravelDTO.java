package com.example.travel.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
@Log4j2
public class UserTravelDTO extends User implements OAuth2User {
    private String userId;
    private String password;
    private Boolean userSocial;

    private String name;
    private String profile;

    private Map<String,Object> attr; //OAuth2User 정보

    public UserTravelDTO(String username,
                         String password,
                         Boolean userSocial,
                         String profile,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = username;
        this.password = password;
        this.userSocial = userSocial;
        this.profile = profile;
    }

    public UserTravelDTO(String username,
                         String password,
                         Boolean userSocial,
                         String profile,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String,Object> attr
    ) {
        this(username,password,userSocial,profile,authorities); // 기존생성자
        this.attr = attr; //OAuth2User 정보
    }

    //OAuth2User 정보
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }



}
