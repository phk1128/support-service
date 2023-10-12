package com.example.supportservice.domain.subscribe.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubscribeStatus {

    SUBSCRIBE(Status.SUBSCRIBE),
    UNSUBSCRIBE(Status.UNSUBSCRIBE);


    private final String status;

    public static class Status {

        public static final String SUBSCRIBE = "구독중";
        public static final String UNSUBSCRIBE = "구독취소";

    }

    public static SubscribeStatus fromString(String status) {
        switch (status) {
            case "SUBSCRIBE":
                return SUBSCRIBE;
            case "UNSUBSCRIBE":
                return UNSUBSCRIBE;
        }
        return null;
    }
}
