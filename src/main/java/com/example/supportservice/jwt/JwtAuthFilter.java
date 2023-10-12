package com.example.supportservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request); // resolveToken()메서드 내부에 문자열을 자르는 부분이 있음, 따라서 "Bearer="은 제외됨

        if(token != null) {
            try {
                jwtUtil.validateToken(token); // validateToken() 매서드에서 만약 ExpiredJwtException(토큰만료)을 던진다면 아래 catch문을 통해 토큰 재발급
            }
            catch (ExpiredJwtException e) {
                token = jwtUtil.reissueAccessToken(token, response).substring(7); // 재발급 받은 액세스토큰에는 "Bearer=" 이 포함되어 있으므로 문자열을 잘라줘야함
                log.info("토큰 재발급 완료");
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject()); // getSubject()를 통해 email을 가져옴
        }

        //다음 필터 진행
        filterChain.doFilter(request, response);

    }

    public void setAuthentication(String email) {
        //JWT 인증 성공 email을 이용해서 인증객체(Authentication) 생성 후 SecurityContext에 저장
        SecurityContext context = SecurityContextHolder.createEmptyContext(); // 시큐리티 정보
        Authentication authentication = jwtUtil.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

    }
}
