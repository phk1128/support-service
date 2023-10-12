package com.example.supportservice.dto;


import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.support.model.Support;
import com.example.supportservice.domain.support.model.SupportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SupportDto {

    @Getter
    public static class Save {

        private Long memberId;
        private Long organizationId;
        private Integer amount;

        public Support toEntity(Member member, Organization organization) {
            return Support.builder()
                    .member(member)
                    .organization(organization)
                    .amount(amount)
                    .supportDate(LocalDateTime.now())
                    .build();


        }




    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long supportId;
        private Long memberId;
        private String supportDate;
        private Integer amount;
        private SupportStatus status;

        public Response toDto(Support support) {

            return Response.builder()
                    .supportId(support.getId())
                    .memberId(support.getMember().getId())
                    .supportDate(support.getSupportDate().toString())
                    .amount(support.getAmount())
                    .status(support.getStatus())
                    .build();


        }

    }
}
