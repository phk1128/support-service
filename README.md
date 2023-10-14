# 후원 서비스

## 개요
후원자가 서비스에 등록되어 있는 기관들 중 원하는 곳에 정기 및 비정기 후원을 자유롭게 할 수 있게 하기 위한 서비스 

### Tech
- #### Language
  Java 11
- #### Framework / Library
  Spring Boot 2.7.15<br>
  Spring MVC<br>
  Spring Data JPA<br>
  Spring Security + JWT<br>

- #### Database
  MySQL / Redis

### About
* Spring Security + Jwt 인증절차 학습을 위한 토이 프로젝트
* 회원가입, 로그인(+카카오 로그인 API) / 기관 등록 / 비정기 후원 & 정기 후원 기능 구현
* Spring Security 를 이용한 회원 인증 처리
* Jwt(access token + refresh token) 기반 stateless 인증
* Redis에서 refresh token를 저장 및 관리 (보안 강화 & 성능 향상)
* 원활한 에러 처리를 위해 ExceptionManager로 에러 통합 관리


### Authorization
1. AccessToken, RefreshToken 발급 절차(with Redis)
   ![image](https://github.com/phk1128/support-service/assets/122284322/f495f532-6a65-4e00-88ee-f9fdab7f5795)
  

* Access Token(In header,cookie): 유지시간 60분<br>
Refresh Token(In Redis): 유지기간 14일<br>


* 통신을 할 때에는 헤더에 액세스토큰을 담는다. 만약 헤더에 토큰이 없는 경우에는 쿠키를 뒤져서 액세스 토큰을 찾는다.
  쿠키에 담아놓은 액세스토큰은 Secure, Http-only(탈취 방지)


* Secure: HTTPS로 통신을 할때만 쿠키를 서버로 보낸다.<br>
  Http-only: JS를 이용해서 토큰을 탈취할 수 없다.


* 서버가 관리하는 인메모리 DB인 Redis에 리프레시토큰을 저장하면 RDB에 저장하고 꺼내오는 것 보다 훨씬 빠르다.


2. Spring Security 인증 절차
   ![image](https://github.com/phk1128/support-service/assets/122284322/4f9fb1c1-bb5e-494e-887e-4b938f50e634)

* JwtAuthFilter (src/main/java/com/example/supportservice/jwt/JwtAuthFilter.java) <br>
유효한 Jwt가 존재한다면 이를 검증하는 커스텀 필터를 통과한다.<br>
해당 필터를 UsernamePasswordAuthenticationFilter 앞에 위치시킨다.


* UsernamePasswordAuthenticationFilter<br>
ID/Password를 사용하는 Form기반 유저 인증을 처리하는 역할을 한다.<br>
Authentication(인증 객체)를 만들어서 ID/PW를 저장하고, AuthenticationManager에게 인증처리를 맡긴다.<br>
인증처리가 완료되면 인증객체를 SecurityContext에 저장한다.




