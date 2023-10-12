package com.example.supportservice.service;

import com.example.supportservice.dto.MemberDto;

public interface KakaoLoginService {

    String getKakaoLoginUrl();
    String getAccessTokenFromKakao(String code);

    MemberDto.Kakao getUserInfoFromKakao(String accessTokenFromKakao);

    MemberDto.Response joinKakaoUser(MemberDto.Kakao dto);
}
