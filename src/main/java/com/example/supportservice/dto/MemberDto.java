package com.example.supportservice.dto;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberJoinType;
import com.example.supportservice.domain.member.model.MemberRoleEnum;
import com.example.supportservice.domain.member.model.MemberStatus;
import lombok.*;



public class MemberDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Join {

        private String email;
        private String password;
        private String name;
        private String role;
        private String joinType;

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .name(name)
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRoleEnum.fromString(role))
                    .joinType(MemberJoinType.fromString(joinType))
                    .build();
        }

    }


    @Getter
    public static class Update {
        private String role;
        private String status;

    }

    @Getter
    public static class UpdatePassword {
        private String beforePassword;
        private String afterPassword;
    }

    @Getter
    public static class Login {
        private String email;
        private String password;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long memberId;
        private String name;
        private MemberStatus status;
        private String joinDate;
        private String message;


        public Response toDto(Member member, String message){

            return Response.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .status(member.getStatus())
                    .joinDate(member.getJoinDate().toString())
                    .message(message)
                    .build();

        }

    }

    @Builder
    @Getter
    public static class Kakao {

        private String socialID;
        private String email;
        private String nickname;
        private String profileImage;
        private String thumbnailImage;

    }
}
