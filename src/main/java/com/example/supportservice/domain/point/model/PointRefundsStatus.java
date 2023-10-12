package com.example.supportservice.domain.point.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointRefundsStatus {

    REFUNDS(Status.REFUNDS),
    PROCESSING(Status.PROCESSING),
    CANCEL(Status.CANCEL);


    private final String status;

    public static class Status {

        public static final String REFUNDS = "환급완료";
        public static final String PROCESSING = "환급처리중";
        public static final String CANCEL = "환급취소";

    }

    public static PointRefundsStatus fromString(String status) {
        switch (status){
            case "REFUNDS":
                return REFUNDS;
            case "CANCEL":
                return CANCEL;

        }

        return null;

    }


}
