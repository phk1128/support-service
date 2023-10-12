package com.example.supportservice.domain.support.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupportStatus {

    SUPPORT(Status.SUPPORT),
    PENDING(Status.PENDING),
    CANCEL(Status.CANCEL);

    private final String status;


    public static class Status {

        private static final String SUPPORT = "후원완료";
        private static final String PENDING = "후원보류";
        private static final String CANCEL = "후원취소";

    }

    public static SupportStatus fromString(String status){
        switch (status) {
            case "후원완료":
                return SUPPORT;
            case "후원보류":
                return PENDING;
            case "후원취소":
                return CANCEL;
        }

        return null;
    }
}
