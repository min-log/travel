package com.example.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
                .loginProcessingUrl("/main")
                .permitAll(); //로그인폼 사용
        http.logout(); //로그아웃폼 사용

        return http.build();
    }
}
