package com.example.supportservice.domain.card.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardCompany {

    KUKMIN(Company.KUKMIN),
    SHINHAN(Company.SHINHAN),
    WOORI(Company.WOORI);

    private final String company;

    public static class Company {
        public static final String KUKMIN = "국민카드";
        public static final String SHINHAN = "신한카드";
        public static final String WOORI = "우리카드";
    }

    public static CardCompany fromString(String company) {
        switch (company) {
            case "KUKMIN":
                return KUKMIN;
            case "SHINHAN":
                return SHINHAN;
            case "WOORI":
                return WOORI;
        }

        return null;
    }
}
