package com.example.supportservice.domain.payment.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {

    CARD("카드결제");

    private final String type;

    public static class Type {
        public static final String CARD = "카드결제";
    }

    public static PaymentType fromString(String type) {
        switch (type) {
            case "CARD":
                return CARD;
        }

        return null;
    }

}
