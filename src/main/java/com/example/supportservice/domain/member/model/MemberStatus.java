package com.example.supportservice.domain.member.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {

    ACTIVE(Status.ACTIVE),
    STOP(Status.STOP),
    SLEEP(Status.SLEEP),
    UNSUBSCRIBE(Status.UNSUBSCRIBE);

    private final String status;

    public static class Status {
        public static final String ACTIVE = "활동";
        public static final String STOP = "정지";
        public static final String SLEEP = "휴면";
        public static final String UNSUBSCRIBE = "탈퇴";

    }

    public static MemberStatus fromString(String status) {
        switch (status) {
            case "ACTIVE":
                return ACTIVE;
            case "STOP":
                return STOP;
            case "SLEEP":
                return SLEEP;
            case "UNSUBSCRIBE":
                return UNSUBSCRIBE;
        }

        return null;

    }
}
