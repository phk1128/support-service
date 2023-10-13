package com.example.supportservice.domain.point.model;


import com.example.supportservice.domain.card.model.CardCompany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointRefundsBank {

    KUKMIN(Bank.KUKMIN),
    SHINHAN(Bank.SHINHAN),
    WOORI(Bank.WOORI);

    private final String bank;

    public static class Bank {
        public static final String KUKMIN = "국민은행";
        public static final String SHINHAN = "신한은행";
        public static final String WOORI = "우리은행";
    }

    public static PointRefundsBank fromString(String bank) {
        switch (bank) {
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
