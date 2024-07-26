# 후원 서비스

## 개요
후원자가 서비스에 등록되어 있는 기관들 중 원하는 곳에 정기 및 비정기 후원을 자유롭게 할 수 있게 하기 위한 서비스,<br> 
비정기 후원은 포인트 충전 후 포인트로 진행, 정기 후원은 회원의 등록된 카드로 매월 정해진 날짜에 자동 결제

### Tech
- #### Language
  Java 11
- #### Framework / Library
  Spring Boot 2.7.15<br>
  Spring MVC<br>
  Spring Data JPA<br>
  Spring Security + JWT<br>

- #### Database
  MySQL(EC2) / Redis(EC2)
- #### CI/CD
  github actions

### About
* github actions를 이용한 CI/CD 파이프라인 구축
* nginx + aws를 활용한 무중단배포
* Spring Security + Jwt 인증절차 학습
* 회원가입, 로그인(+카카오 로그인 API) / 포인트 충전 및 사용 / 기관 등록 / 비정기 후원 & 정기 후원 / 정기 후원 갱신 기능 구현
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

- 인증이 이루어지는 과정
  - 스프링 시큐리티의 UsernamePasswordAuthenticationFilter가 클라이언트의 요청을 가로채서 인증 절차를 수행한다.
  - 요청에서 username과 password를 추출한다.
  - 추출한 정보를 이용해서 UsernamePasswordAuthenticationToken 객체를 생성한다.
  - UsernamePasswordAuthenticationToken 객체를 AuthenticationManager에 전달하여 인증을 시도한다.
    - AuthenticationManager(AM)는 UsernamePasswordAuthenticationToken(UPAT)를 AuthenticationProvider(AP)에게 보낸다.
    - AP는 UPAT에서 클라이언트의 아이디(이메일)를 UseDetailsService에 전달하여 존재하는 클라이언트인지 확인한다.
    - UseDetailsService는 DB에 접근해서 존재하는 클라이언트라면 UserDetails 객체를 반환하고 존재하지 않으면 예외를 발생시킨다. (UserDetails 객체에는 아이디와 비밀번호가 들어있다.)
    - UserDetails 객체가 성공적으로 반환되었다면 AP는 UserDetails 객체에서 비밀번호를 꺼내서 앤코더를 이용해서 클라이언트가 요청에 함께 보낸 비밀번호와 일치하는지 확인한다.
    - 만약 일치한다면 인증된 Authentication 객체를 SecurityContext에 저장한다.
  - 인증이 성공했다면 성공핸들러를 통해 추가 작업을 진행하고, 실패했다면 실패핸들러를 통해 추가작업을 진행한다.


- Jwt를 사용할때 UsernamePasswordAuthenticationFilter 상속 받아서 커스텀 필터를 구현하는 이유
  - 로그인 과정에서 JWT 토큰을 발급하고 이를 응답으로 클라이언트에게 전달하기 위함이다. 기본적으로 스프링 시큐리티는 세션 기반 인증을 사용하기 때문에 JWT를 사용하려면 로그인 과정에 약간의 커스텀 처리가 필요하다.
  - JWT를 발급하고 응답 헤더에 추가하는 기능을 커스텀 하는 것이다.
  - UsernamePasswordAuthenticationFilter 상속받아 커스텀 필터를 구현하면, 기존의 인증처리 흐름을 변경하지 않고 JWT 발급 기능을 추가할 수 있음, 이는 스프링 시큐리티의 일관된 인증 처리 흐름을 유지하는 데 도움이 된다.

### Infra
![image](https://github.com/user-attachments/assets/7921de66-622f-4a9e-bea3-4553599da8e6)
- 클라이언트가 운영서버에 요청을 보내면 nginx는 리버스 프록시기능을 이용해 해당 요청을 springboot서버에 전달한다.
- 배포시에는 블루, 그린 중 현재 꺼져 있는 놈으로 컨테이너를 빌드한다

### CI/CD
![image](https://github.com/user-attachments/assets/7920f57a-573e-4e96-a361-3d5ea391e1fe)
- CI/CD는 아래와 같이 순차적으로 진행된다
  - master 브랜치에 push 이벤트가 발생하면 github actions가 동작한다
  - springboot 어플리케이션을 테스트 및 빌드한다
  - docker 이미지를 빌드한 뒤 docker hub에 푸시한다
  - 운영서버(ec2)에 ssh 접속하여 해당 docker 이미지를 docker hub에서 당겨온다
  - 현재 구동중인 컨테이너가 블루인지 그린인지 판단한다
  - docker compose를 통해 현재 중지된 컨테이너를 빌드한다 (만약 블루가 구동중이라면 그린이 빌드됨)
  - 빌드된 서버를 헬스체크한다
  - 정상적으로 빌드가 완료 되었다면 nginx 리버스 프록시 대상을 해당 컨테이너로 설정한다
  - 현재 구동되고 있던 컨테이너를 중지한다

### ERD
![image](https://github.com/phk1128/support-service/assets/122284322/da9d92d3-a091-4786-8e78-cae2db672846)





