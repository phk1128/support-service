package com.example.supportservice.domain.payment.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    PAYMENT(Status.PAYMENT),
    PENDING(Status.PENDING),
    CANCEL(Status.CANCEL);


    private final String status;

    public static class Status {

        public static final String PAYMENT = "결제완료";
        public static final String PENDING = "결제보류";
        public static final String CANCEL = "결제취소";

    }

    public static PaymentStatus fromString(String status) {
        switch (status){
            case "PAYMENT":
                return PAYMENT;
            case "PENDING":
                return PENDING;
            case "CANCEL":
                return CANCEL;

        }

        return null;

    }

}
