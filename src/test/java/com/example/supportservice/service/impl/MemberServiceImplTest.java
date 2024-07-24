package com.example.supportservice.service.impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberJoinType;
import com.example.supportservice.domain.member.model.MemberRoleEnum;
import com.example.supportservice.domain.member.model.MemberStatus;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.member.repository.RefreshTokenRepository;
import com.example.supportservice.dto.MemberDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.jwt.JwtUtil;
import com.example.supportservice.service.Impl.MemberServiceImpl;
import com.example.supportservice.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceImplTest {

    /*
    기능을 테스트 할 MemberServiceImpl은 @Autowired를 이용하여
    SpringBoot가 자동으로 의존성을 주입하게 한다. MemberServiceImpl에서
    주입 받은 객체들은 @MockBean을 이용하여 모의 객체로 생성한다.

    예로 MemberRepository를 실제 객체를 사용할 경우 실제로 데이터베이스를 조회하므로,
    테스트코드가 데이터베이스에 영향을 줄수 도 있다.
    */

    @Autowired
    private MemberServiceImpl memberService;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private PointService pointService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

//    @Test
//    @DisplayName("회원 조회 성공 테스트")
//    public void 회원정보조회() {
//
//        // given
//        Member member = Member.builder()
//                .email("test@test.com")
//                .name("박형균")
//                .password("encodedPassword")
//                .status(MemberStatus.ACTIVE)
//                .role(MemberRoleEnum.MEMBER)
//                .joinType(MemberJoinType.HOME)
//                .build();
//
//        Long memberId = 1L;
//
//        Mockito.when(memberRepository.findById(memberId))
//                .thenReturn(Optional.of(member));
//
//        // when
//        MemberDto.Response memberResponseDto = memberService.getMember(1L);
//
//        // then
//        assertThat(memberResponseDto.getEmail()).isEqualTo(member.getEmail());
//        assertThat(memberResponseDto.getName()).isEqualTo(member.getName());
//
//        verify(memberRepository).findById(memberId);
//
//
//    }

//    @Test
//    @DisplayName("회원 조회 실패 테스트 - 회원 정보 찾을 수 없음")
//    public void 회원정보조회실패() {
//        // given
//        Long memberId = 1L;
//
//        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
//
//        // when
//        // then
//
//        assertThatThrownBy(() -> memberService.getMember(memberId))
//                .isInstanceOf(AppException.class)
//                .hasMessageContaining("회원 정보를 찾을 수 없습니다.");
//
//        verify(memberRepository).findById(memberId);
//
//
//
//
//    }


}
