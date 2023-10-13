package com.example.supportservice.service;

import com.example.supportservice.dto.SupportDto;

import java.util.List;

public interface SupportService {

    SupportDto.Response save(SupportDto.Save dto);

    List<SupportDto.Response> getAllSupport(Long memberId);



}
