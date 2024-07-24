package com.example.supportservice.security.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Slf4j
@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRoleEnum role = member.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority); // role을 추가
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority); // role을 담는다.

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }/*false: 사용자 계정의 유효 기간 만료*/

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }/*false: 계정 잠금 상태*/

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }/*false: 비밀번호 만료*/

    @Override
    public boolean isEnabled() {
        return true;
    } /*false: 유효하지 않은 사용자*/
}
