package com.example.supportservice.domain.organization.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationStatus {

    OPENED(Status.OPENED),
    CLOSED(Status.CLOSED);

    private final String status;

    public static class Status {
        public static final String OPENED = "개관";
        public static final String CLOSED = "폐관";

    }

    public static OrganizationStatus fromString(String status) {
        switch (status) {
            case "OPENED":
                return OPENED;
            case "CLOSED":
                return CLOSED;
        }

        return null;

    }



}
