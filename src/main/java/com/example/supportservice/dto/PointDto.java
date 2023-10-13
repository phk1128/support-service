package com.example.supportservice.dto;

import com.example.supportservice.domain.payment.model.PaymentStatus;
import com.example.supportservice.domain.point.model.Point;
import com.example.supportservice.domain.point.model.PointRefunds;
import com.example.supportservice.domain.point.model.PointRefundsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PointDto {


    @Getter
    public static class Charge {

        private String cardNumber;
        private String cardCompany;
        private Integer amount;
        private String paymentType;

    }

    @Getter
    public static class Refunds {

        private String bank;
        private String account;
        private Integer amount;

        public PointRefunds toEntity(Point point) {
            return PointRefunds.builder()
                    .bank(PointRefundsBank.fromString(bank))
                    .account(account)
                    .amount(amount)
                    .requestDate(LocalDateTime.now())
                    .build();

        }

    }

    @Getter
    public static class UpdateRefunds {

        private boolean result;

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponsePoint {

        private Long memberId;
        private Integer chargedPoint;
        private Integer remainPoint;
        private Integer usedPoint;

        public ResponsePoint toDto(Point point) {
            return ResponsePoint.builder()
                    .memberId(point.getMember().getId())
                    .chargedPoint(point.getChargedPoint())
                    .remainPoint(point.getRemainPoint())
                    .usedPoint(point.getUsedPoint())
                    .build();

        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponsePointRefunds {

        private Long pointRefundsId;
        private PointRefundsBank bank;
        private String account;
        private Integer amount;
        private String requestDate;
        private String completionDate;
        private PointRefundsStatus status;

        public ResponsePointRefunds toDto(PointRefunds pointRefunds) {
            return ResponsePointRefunds.builder()
                    .pointRefundsId(pointRefunds.getId())
                    .bank(pointRefunds.getBank())
                    .account(pointRefunds.getAccount())
                    .amount(pointRefunds.getAmount())
                    .requestDate(pointRefunds.getRequestDate().toString())
                    .completionDate(pointRefunds.getCompletionDate() == null ? "" : pointRefunds.getCompletionDate().toString())
                    .status(pointRefunds.getStatus())
                    .build();

        }
    }
}
