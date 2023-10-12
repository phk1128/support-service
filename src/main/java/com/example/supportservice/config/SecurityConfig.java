package com.example.supportservice.config;

import com.example.supportservice.domain.member.model.MemberRoleEnum;
import com.example.supportservice.jwt.JwtAuthFilter;
import com.example.supportservice.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // resources 자원 접근 허용
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //csrf 설정 해제
        httpSecurity.csrf().disable();

        //JWT 사용을 위해 기존의 세션 방식 인증 해제
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //URL Mapping
        httpSecurity.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/member/join").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/member/login").permitAll()
                .antMatchers("/api/v1/kakao/**").permitAll()
                .antMatchers("/admin").hasRole(MemberRoleEnum.ADMIN.toString())
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        /*로그인 페이지 설정
        1. 인증이 되지 않은 사용자가 permitAll()페이지가 아닌 페이지에 접근할 때 /login으로 강제 이동 시킨다.
        2. 이때의 인증은 위에 필터에 등록해 놓은 JwtAuthFilter를 통해 JWT 토큰의 유무(유효성 검증) 기준이다.
        */
//        httpSecurity.formLogin().loginPage("/api/v1/member/login");

        httpSecurity.exceptionHandling().accessDeniedPage("/api/v1/member/forbidden");

        return httpSecurity.build();

    }

}

