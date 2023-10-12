package com.example.supportservice.domain.organization.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationStatus {

    ENABLE(Status.ENABLE),
    DISABLE(Status.DISABLE);

    private final String status;

    public static class Status {
        public static final String ENABLE = "후원가능";
        public static final String DISABLE = "후원불가능";

    }

    public static OrganizationStatus fromString(String status) {
        switch (status) {
            case "ENABLE":
                return ENABLE;
            case "DISABLE":
                return DISABLE;
        }

        return null;

    }



}
