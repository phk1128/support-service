package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberRoleEnum;
import com.example.supportservice.domain.member.model.MemberStatus;
import com.example.supportservice.domain.member.model.RefreshToken;
import com.example.supportservice.domain.point.model.Point;
import com.example.supportservice.domain.point.repository.PointRepository;
import com.example.supportservice.dto.MemberDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.jwt.JwtUtil;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.member.repository.RefreshTokenRepository;
import com.example.supportservice.service.MemberService;
import com.example.supportservice.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PointService pointService;


    @Override
    public MemberDto.Response getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원 정보를 찾을 수 없습니다."));
        return new MemberDto.Response().toDto(member, "회원정보 조회 완료");
    }

    @Override
    @Transactional
    public MemberDto.Response join(MemberDto.Join dto) {

        if(memberRepository.existsByEmail(dto.getEmail())) {
            throw new AppException(ErrorCode.MEMBER_EMAIL_DUPLICATED,dto.getEmail() + "는 이미 존재하는 email입니다.");
        }

        Member newMember = memberRepository.save(dto.toEntity());

        pointService.save(newMember);

        newMember.updatePassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        return new MemberDto.Response().toDto(newMember, "신규가입완료");

    }

    @Override
    @Transactional
    public MemberDto.Response login(String email, String password, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "email을 다시 확인해주세요"));

        //password 확인
        //matches(받은값, DB에서 가져온값) 매개변수의 순서 중요
        if(!bCryptPasswordEncoder.matches(password,member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 확인해주세요");
        }


        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole(), response);

        //리프레시 토큰에는 회원의 정보를 담아둘 필요가 없다.
        String refreshToken = jwtUtil.createRefreshToken();

        log.info(accessToken);
        log.info(refreshToken);

        //리프레시 토큰은 Redis에 저장
        refreshTokenRepository.save(new RefreshToken(String.valueOf(member.getId()), refreshToken, accessToken));


        return new MemberDto.Response().toDto(member, "로그인완료");


    }

    @Override
    @Transactional
    public MemberDto.Response update(Long memberId, MemberDto.Update dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));

        member.update(MemberStatus.fromString(dto.getStatus()), MemberRoleEnum.fromString(dto.getRole()));


        return new MemberDto.Response().toDto(memberRepository.save(member), "회원 정보 수정 완료");
    }

    @Override
    @Transactional
    public MemberDto.Response updatePassword(Long memberId, MemberDto.UpdatePassword dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));


        if(dto.getBeforePassword().equals(dto.getAfterPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "이전과 다른 패스워드로 설정해주세요");
        }

        //password 확인
        //matches(받은값, DB에서 가져온값) 매개변수의 순서 중요
        if(!bCryptPasswordEncoder.matches(dto.getBeforePassword(), member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "이전 패스워드를 확인해주세요");
        }

        member.updatePassword(bCryptPasswordEncoder.encode(dto.getAfterPassword()));


        return new MemberDto.Response().toDto(memberRepository.save(member), "비밀번호 변경이 완료되었습니다.");
    }


}
