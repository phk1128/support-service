package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberStatus;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.organization.repository.OrganizationRepository;
import com.example.supportservice.domain.point.model.Point;
import com.example.supportservice.domain.point.repository.PointRepository;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import com.example.supportservice.domain.subscribe.model.SubscribeStatus;
import com.example.supportservice.domain.support.model.Support;
import com.example.supportservice.domain.support.model.SupportStatus;
import com.example.supportservice.domain.support.repository.SupportRepository;
import com.example.supportservice.dto.SupportDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.CardService;
import com.example.supportservice.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;
    private final PointRepository pointRepository;

    @Override
    @Transactional
    public List<SupportDto.Response> getAllSupport(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        return supportRepository.findAllByMember(member).stream()
                .map(support -> new SupportDto.Response().toDto(support))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupportDto.Response save(SupportDto.Save dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다."));

        Organization organization = organizationRepository.findById(dto.getOrganizationId())
                .orElseThrow(() -> new AppException((ErrorCode.ORGANIZATION_NOT_FOUND), "후원기관을 찾을 수 없습니다."));

        Point point = pointRepository.findByMember(member)
                .orElseThrow(() -> new AppException((ErrorCode.POINT_NOT_FOUND), "포인트 정보를 찾을 수 없습니다."));


        int remainPoint = point.getRemainPoint() - dto.getAmount();
        int usedPoint = point.getUsedPoint() + dto.getAmount();


        if (remainPoint < 0) {
            throw new AppException(ErrorCode.SUPPORT_CONFLICT, "보유하고 있는 후원금이 부족합니다.");
        }

        point.updateRemainAndUsedPoint(usedPoint, remainPoint);

        pointRepository.save(point);
        memberRepository.save(member);

        Support newSupport = supportRepository.save(dto.toEntity(member, organization));
        newSupport.updateStatus(SupportStatus.SUPPORT);

        return new SupportDto.Response().toDto(supportRepository.save(newSupport));
    }


}
