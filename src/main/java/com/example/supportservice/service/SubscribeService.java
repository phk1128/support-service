package com.example.supportservice.service;

import com.example.supportservice.dto.SubscribeDto;

import java.util.List;

public interface SubscribeService {

    SubscribeDto.Response save(Long memberId, SubscribeDto.Save dto);

    List<String> updateAllSubscribe();


}
