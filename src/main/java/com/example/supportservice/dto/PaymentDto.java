package com.example.supportservice.dto;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.payment.model.Payment;
import com.example.supportservice.domain.payment.model.PaymentItem;
import com.example.supportservice.domain.payment.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

public class PaymentDto {

//    @Getter
//    @Builder
//    public static class Save {
//
//        private Long memberId;
//        private Integer amount;
//        private String type;
//        private String item;
//
//        public Payment toEntity(Member member) {
//            return Payment.builder()
//                    .member(member)
//                    .amount(amount)
//                    .item(PaymentItem.fromString(item))
//                    .paymentDate(LocalDateTime.now())
//                    .build();
//
//        }
//
//
//    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long paymentId;
        private Long memberId;
        private Integer amount;
        private String paymentDate;
        private PaymentStatus status;
        private PaymentItem item;


        public Response toDto(Payment payment) {
            return Response.builder()
                    .paymentId(payment.getId())
                    .memberId(payment.getMember().getId())
                    .amount(payment.getAmount())
                    .paymentDate(payment.getPaymentDate().toString())
                    .status(payment.getStatus())
                    .item(payment.getItem())
                    .build();
        }
    }
}
