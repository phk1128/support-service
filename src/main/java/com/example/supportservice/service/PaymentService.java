package com.example.supportservice.service;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import com.example.supportservice.dto.PaymentDto;

public interface PaymentService {

    PaymentDto.Response save(String cardNumber , Member member, Integer amount, String type, String item, Subscribe subscribe);



}
