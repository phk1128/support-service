package com.example.supportservice.service;

import com.example.supportservice.dto.MemberDto;

import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    MemberDto.Response getMember(Long memberId);

    MemberDto.Response join(MemberDto.Join dto);

    MemberDto.Response login(String email, String password, HttpServletResponse response);

    MemberDto.Response update(Long memberId, MemberDto.Update dto);

    MemberDto.Response updatePassword(Long memberId, MemberDto.UpdatePassword dto);
}
