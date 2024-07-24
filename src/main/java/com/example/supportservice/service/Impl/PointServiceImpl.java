package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.point.model.Point;
import com.example.supportservice.domain.point.model.PointRefunds;
import com.example.supportservice.domain.point.model.PointRefundsStatus;
import com.example.supportservice.domain.point.repository.PointRepository;
import com.example.supportservice.domain.point.repository.PointRefundsRepository;
import com.example.supportservice.dto.PointDto;
import com.example.supportservice.dto.PointDto.ResponsePoint;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.PaymentService;
import com.example.supportservice.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    private final PaymentService paymentService;
    private final PointRefundsRepository pointRefundsRepository;

    @Override
    @Transactional
    public void save(Member member) {

        Point newPoint = Point.builder()
                .chargedPoint(0)
                .remainPoint(0)
                .usedPoint(0)
                .build();

        newPoint.setMember(member);

        pointRepository.save(newPoint);

    }

    @Override
    @Transactional
    public PointDto.ResponsePoint charge(Long memberId,PointDto.Charge dto) throws AppException{

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));

        Point point = pointRepository.findByMember(member)
                .orElseThrow(() -> new AppException((ErrorCode.POINT_NOT_FOUND), "포인트 정보를 찾을 수 없습니다."));

        paymentService.save(dto.getCardNumber(),member, dto.getAmount(), dto.getPaymentType(), "POINT",null);
        point.chargePoint(dto.getAmount());

        return new PointDto.ResponsePoint().toDto( pointRepository.save(point));
    }

    @Override
    @Transactional
    public PointDto.ResponsePointRefunds refunds(Long memberId, PointDto.Refunds dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));

        Point point = pointRepository.findByMember(member)
                .orElseThrow(() -> new AppException((ErrorCode.POINT_NOT_FOUND), "포인트 정보를 찾을 수 없습니다."));

        int remain = point.getRemainPoint() - dto.getAmount();

        if(remain < 0) {
            throw new AppException(ErrorCode.POINT_REFUNDS_CONFLICT, "환급 가능한 포인트가 부족합니다.");
        }



        PointRefunds newPointRefunds = dto.toEntity(point);
        newPointRefunds.update(PointRefundsStatus.PROCESSING, null);


        return new PointDto.ResponsePointRefunds().toDto(pointRefundsRepository.save(newPointRefunds));
    }

    @Override
    @Transactional
    public PointDto.ResponsePointRefunds updateRefunds(Long pointRefundsId, PointDto.UpdateRefunds dto) {
        PointRefunds pointRefunds = pointRefundsRepository.findById(pointRefundsId)
                .orElseThrow(() -> new AppException(ErrorCode.POINT_REFUNDS_NOT_FOUND, "포인트 환급 내역이 없습니다."));

        PointRefundsStatus status = PointRefundsStatus.CANCEL;
        LocalDateTime completionDate = null;

        if(dto.isResult()) {
            status = PointRefundsStatus.REFUNDS;
            completionDate = LocalDateTime.now();
        }
        else {
            Point point = pointRefunds.getPoint();
            int remainPoint = point.getRemainPoint() + pointRefunds.getAmount();
            int usedPoint = point.getUsedPoint();

            point.updateRemainAndUsedPoint(remainPoint, usedPoint);
            pointRepository.save(point);
        }

        pointRefunds.update(status, completionDate);


        return new PointDto.ResponsePointRefunds().toDto(pointRefundsRepository.save(pointRefunds));
    }
}
