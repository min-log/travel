package com.example.travel.config;

import com.example.travel.security.service.CustomAuthFailureHandler;
import com.example.travel.security.service.UserTravelDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserTravelDetailsService userDetailService; //로그인기능
    private final CustomAuthFailureHandler loginFailHandler;


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/","/admin").permitAll()
                .antMatchers("/mypage").hasAnyRole("USER","ADMIN")
                .antMatchers("/mypage/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/travel").hasAnyRole("USER","ADMIN")
                .antMatchers("/travel/**").hasAnyRole("USER")
                .antMatchers("/admin/m/**").hasRole("ADMIN")
        .and().formLogin()
                .loginPage("/member/loginForm")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/security-login", true) // 회원정보 저장:  두번째 인자가 true일경우 무조건 로그인시 해당 페이지 이동
                .failureUrl("/member/loginForm")
                .failureHandler(loginFailHandler)

        //소셜 로그인 구글 / 네이버 / 카카오
        .and().oauth2Login()
                .defaultSuccessUrl("/security-login" ,true) // 회원정보 저장; //소셜 로그인 구글 추가

        // 자동 로그인
        .and().rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(60*60*24*7)
                .alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
                .userDetailsService(userDetailService) //기능 사용시 정보 필요

        .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/security-login")
        //접근권한 없는 페이지 이동
        .and()
                .exceptionHandling().accessDeniedPage("/access-denied");


        //http.headers().frameOptions().sameOrigin(); // 스마트 에디터 오류 수정 -- 네이버
        //http.csrf().disable();

        return http.build();
    }





    // RequestRejectedException 오류 제거
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }




}
