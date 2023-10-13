package com.example.supportservice.jwt;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberRoleEnum;
import com.example.supportservice.domain.member.model.RefreshToken;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.member.repository.RefreshTokenRepository;
import com.example.supportservice.security.Impl.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer=";
    private static final long TOKEN_TIME = 60*60*1000L; // 토큰 유효시간 60분
    private static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 리프레쉬 토큰 유효기간 14일

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    //의존성이 주입이 완료된 후 자동으로 1회 실행된다. 다른 리소스에서 호출되지 않아도 수행된다.
    //bean의 생애주기에서 오직 한번만 수행된다는것을 보장한다.(어플리케이션이 실행될때 한번만 실행)
    //bean이 여러번 초기화되는 걸 방지
    @PostConstruct
    public void init() {

        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

    }

    //header 토큰을 가져오기 -> 헤더검사 없으면 쿠키검사
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        //헤더에 값이 없다면 토큰 확인
        if(bearerToken == null) {
            Cookie[] cookies = request.getCookies();// 모든 쿠키 가져오기
            if (cookies != null) {
                for (Cookie c :cookies) {
                    String name = c.getName(); // 쿠키 이름 가져오기

                    String value = c.getValue(); // 쿠기 값 가져오기

                    if(name.equals(AUTHORIZATION_HEADER)) {
                        bearerToken = value;
                    }
                }
            }
        }
        // bearerToken이 null이 아니고 공백이 아닌 문자열인지 확인 && BEARER_PREFIX로 시작하는지 확인
        // 조건이 만족하면 bearerToken의 7번째 인덱스 부터 문자열 끝까지 반환 -> "Bearer=" 문자열을 제거한다는말
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);

        }
        return null;
    }

    //토큰 생성
    public String createAccessToken(String email, MemberRoleEnum role, HttpServletResponse response) {
        Date date = new Date();

        String accessToken = BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();

        //액세스토큰은 쿠키에 저장해둔다.
        Cookie accessTokenCookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setMaxAge(14 * 24 * 60 * 60); // 쿠키에 담는 액세스 토큰은 14일 동안 유지
        accessTokenCookie.setPath("/");
        accessTokenCookie.setDomain("localhost");
        response.addCookie(accessTokenCookie); // 쿠키에 저장

        response.addHeader("token", accessToken);
        response.addHeader("email",email);

        return accessToken;

    }

    /*Refresh 토큰 생성
     * 리프레시토큰에는 회원의 정보를 담아 둘 필요가 없다.
     * */
    public String createRefreshToken() {

        Claims claims = Jwts.claims();

        Date date = new Date();
        Date expireDate = new Date(date.getTime() + REFRESH_TOKEN_TIME);



        return BEARER_PREFIX +
                Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(expireDate)
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();

    }

    // 토큰 검증
    public void validateToken(String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    }
    // 인증 객체(Authentication) 생성
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email); // email로 사용자 존재 유무 검증
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 매개변수(사용자 정보, 비밀번호, 권한)

    }

    //액세스 토큰 재발급
    public String reissueAccessToken(String token, HttpServletResponse response){
        RefreshToken refreshTokenInfo = refreshTokenRepository.findByAccessToken(BEARER_PREFIX + token)
                .orElseThrow(() -> new AppException((ErrorCode.TOKEN_NOT_FOUND), "리프레시 토큰 정보를 찾을 수 없습니다."));

        Long memberId = Long.valueOf(refreshTokenInfo.getId());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "리프레시 토큰 정보에 있는 회원을 찾을 수 없습니다."));

        String newAccessToken = createAccessToken(member.getEmail(), member.getRole(), response);

        refreshTokenInfo.updateAccessToken(newAccessToken);

        refreshTokenRepository.save(refreshTokenInfo);

        return newAccessToken;


    }
}
