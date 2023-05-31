package com.example.travel.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
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
//        http.formLogin()
//                .loginPage("/loginForm")
//                .loginProcessingUrl("/login_proc")
//                .defaultSuccessUrl("/main")
//                .permitAll()
//                .and().logout().permitAll(); //로그아웃폼 사용



        http.formLogin(login->
                login.loginPage("/loginForm")
                        .loginProcessingUrl("/login_proc")
                        .permitAll()
                        .defaultSuccessUrl("/main", false)
                        .failureUrl("/login-error")
        );

        return http.build();
    }
}
