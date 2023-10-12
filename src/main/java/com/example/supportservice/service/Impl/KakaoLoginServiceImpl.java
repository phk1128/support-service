package com.example.supportservice.service.Impl;

import com.example.supportservice.config.KakaoApiConfig;
import com.example.supportservice.dto.MemberDto;
import com.example.supportservice.service.KakaoLoginService;
import com.example.supportservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final KakaoApiConfig kakaoApiConfig;
    private final MemberService memberService;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String tokenUrl;
    private String userinfoUrl;



    @PostConstruct
    private void init() {
        clientId = kakaoApiConfig.getClientId();
        clientSecret = kakaoApiConfig.getClientSecret();
        redirectUri = kakaoApiConfig.getRedirectUri();
        grantType = kakaoApiConfig.getGrantType();
        tokenUrl = kakaoApiConfig.getTokenUrl();
        userinfoUrl = kakaoApiConfig.getUserinfoUrl();
    }

    @Override
    @Transactional
    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }


    @Override
    @Transactional
    public String getAccessTokenFromKakao(String code) {

        //헤더 생성 headerName: "Content-Type" headerValue: "application/x-www-form-urlencoded;charset=utf-8"
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //키값의 중복을 방지하기 위해 Map, HashMap이 아닌 MultiValueMap을 사용
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);

        //헤더와 바디 엮기
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        /*POST 요청하기*/
        RestTemplate restTemplate = new RestTemplate();
        //(요청보낼주소, 요청보낼데이터, 요청시 반환되는 데이터 타입)
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
        Map mapResponseEntityBody = mapResponseEntity.getBody();

        //반환받은 Map에서 키값이 "access_token"인 데이터를 가져오기
        return (String) Optional.ofNullable(mapResponseEntityBody)
                .map(response -> response.get("access_token"))
                .orElse(null);

    }

    @Override
    @Transactional
    public MemberDto.Kakao getUserInfoFromKakao(String accessToken){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> StringHttpEntity = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(userinfoUrl, HttpMethod.GET, StringHttpEntity, Map.class);
        Map<String, Object> mapResponseEntityBody = mapResponseEntity.getBody();


      /*
      email, nickname은 각각 kakaoAccount, properties Json안에 존재하기 때문에 Map형태로 한번더 가져와야함
      */

        //방법1 제네릭을 통해 Map 형태의 Key(String) : Value(String) 으로 되어 있음을 알려줘야함


        String email = getInfo(mapResponseEntityBody, "kakao_account", "email");

        String profileImage = getInfo(mapResponseEntityBody, "properties", "profile_image");

        String thumbnailImage = getInfo(mapResponseEntityBody, "properties", "thumbnail_image");
        //방법2
        String nickname = Optional.ofNullable(mapResponseEntityBody)
                .map(response -> {
                    Map<String,String> properties = (Map<String, String>) response.get("properties");
                    return properties.get("nickname");
                })
                .orElse(null);


        String socialID = Optional.ofNullable(mapResponseEntityBody)
                .map(response -> response.get("id"))
                .map(id -> id.toString())
                .orElse(null);

        MemberDto.Kakao kakaoDto = MemberDto.Kakao.builder()
                .socialID(socialID)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .thumbnailImage(thumbnailImage)
                .build();

        return kakaoDto;


    }

    @Override
    @Transactional
    public MemberDto.Response joinKakaoUser(MemberDto.Kakao dto) {

        MemberDto.Join memberJoinDto = MemberDto.Join.builder()
                .joinType("KAKAO")
                .name(dto.getNickname())
                .password(dto.getSocialID())
                .email(dto.getEmail())
                .role("ROLE_MEMBER")
                .build();

        return memberService.join(memberJoinDto);

    }

    private static String getInfo(Map<String, Object> mapResponseEntityBody, String category, String info) {
        return Optional.ofNullable(mapResponseEntityBody)
                .map(response -> ((Map<String, String>) response.get(category)).get(info))
                .orElse(null);
    }
}
