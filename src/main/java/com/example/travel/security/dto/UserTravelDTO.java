package com.example.travel.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserTravelDTO extends User {
    private String userId;
    private String userPassword;
    private Boolean userSocial;

    public UserTravelDTO(String username, String password,
                         Boolean userSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = username;
        this.userSocial = userSocial;
    }
}
