package com.example.supportservice.security.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    //사용자가 존재하면 정보를 이용해 UserDetails객체 생성(UserDetails에는 사용자의 정보가 담긴다.)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new UserDetailsImpl(member, member.getPassword(), member.getEmail());

    }
}
