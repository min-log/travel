package com.example.travel.config;

import com.example.travel.security.service.UserTravelDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Log4j2
@Configuration
public class SecurityConfig {

    @Autowired
    private UserTravelDetailsService userDetailService; //로그인기능

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
        ;
        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/main")
                .failureUrl("/loginForm");



        // 자동 로그인
        http.rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(60*60*24*7)
                .alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
                .userDetailsService(userDetailService); //기능 사용시 정보 필요

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/loginForm");



        return http.build();
    }
}
