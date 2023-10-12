package com.example.supportservice.domain.member.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRoleEnum {

    MEMBER(Authority.MEMBER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    public static class Authority {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String ADMIN = "ROLE_ADMIN";

    }

    public static MemberRoleEnum fromString(String role){
        switch (role){
            case "ROLE_MEMBER":
                return MEMBER;
            case "ROLE_ADMIN":
                return ADMIN;
        }

        return null;

    }
}
