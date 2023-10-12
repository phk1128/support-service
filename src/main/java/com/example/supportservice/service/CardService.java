package com.example.supportservice.service;

import com.example.supportservice.domain.member.model.Member;

public interface CardService {

    void save(String cardCompany, String cardNumber, Member member);
    String cardValidator(Member member);
}
