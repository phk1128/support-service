package com.example.supportservice.domain.member.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberJoinType {


    HOME(Type.HOME),
    KAKAO(Type.KAKAO);


    private final String type;

    public static class Type {
        public static final String HOME = "HOME";
        public static final String KAKAO = "KAKAO";

    }

    public static MemberJoinType fromString(String joinType) {
        switch (joinType) {
            case "HOME":
                return HOME;
            case "KAKAO":
                return KAKAO;

        }
        return null;

    }
}
