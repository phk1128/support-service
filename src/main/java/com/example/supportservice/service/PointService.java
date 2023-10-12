package com.example.supportservice.service;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.point.model.PointRefunds;
import com.example.supportservice.dto.PointDto;

public interface PointService {


    void save(Member member);
    PointDto.ResponsePoint charge(PointDto.Charge dto);

    PointDto.ResponsePointRefunds refunds(Long pointId, PointDto.Refunds dto);

    PointDto.ResponsePointRefunds updateRefunds(Long pointRefundsId, PointDto.UpdateRefunds dto);
}
