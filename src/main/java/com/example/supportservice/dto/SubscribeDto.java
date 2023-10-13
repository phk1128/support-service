package com.example.supportservice.dto;

import com.example.supportservice.domain.payment.model.Payment;
import com.example.supportservice.domain.payment.model.PaymentItem;
import com.example.supportservice.domain.payment.model.PaymentStatus;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import com.example.supportservice.domain.subscribe.model.SubscribeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SubscribeDto {

    @Getter
    public static class Save {

        private String paymentType;
        private String cardNumber;
        private String cardCompany;
        private Long organizationId;
        private Integer amount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long memberId;
        private Long organizationId;
        private String startDate;
        private String endDate;
        private SubscribeStatus status;
        private Integer amount;


        public SubscribeDto.Response toDto(Subscribe subscribe) {
            return Response.builder()
                    .memberId(subscribe.getMember().getId())
                    .organizationId(subscribe.getOrganization().getId())
                    .amount(subscribe.getAmount())
                    .startDate(subscribe.getStartDate().toString())
                    .endDate(subscribe.getEndDate().toString())
                    .status(subscribe.getStatus())
                    .build();
        }
    }

}
