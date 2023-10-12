package com.example.supportservice.controller;

import com.example.supportservice.dto.MemberDto;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.service.KakaoLoginService;
import com.example.supportservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/kakao")
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    //카카오 로그인폼 호출(리다이렉트)를 위해서는 GetMapping으로 해야함
    @GetMapping("/login")
    public RedirectView kakaoLogin() {
        return new RedirectView(kakaoLoginService.getKakaoLoginUrl());
    }


    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) {
        String accessTokenFromKakao = kakaoLoginService.getAccessTokenFromKakao(code);
        MemberDto.Kakao userInfoFromKakao = kakaoLoginService.getUserInfoFromKakao(accessTokenFromKakao);

        if(!memberRepository.existsByEmail(userInfoFromKakao.getEmail())){

            return ResponseEntity.ok(kakaoLoginService.joinKakaoUser(userInfoFromKakao));
        }
        return ResponseEntity.ok(memberService.login(userInfoFromKakao.getEmail(), userInfoFromKakao.getSocialID(), response));


    }
}
