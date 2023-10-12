package com.example.supportservice.domain.payment.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentItem {


    POINT(Item.POINT),
    SUBSCRIBE(Item.SUBSCRIBE);

    private final String item;

    public static class Item {

        public static final String POINT = "포인트결제";
        public static final String SUBSCRIBE = "구독결제";
    }


    public static PaymentItem fromString(String item) {
        switch (item){
            case "POINT":
                return POINT;
            case "SUBSCRIBE":
                return SUBSCRIBE;
        }

        return null;
    }

}
