package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.member.model.MemberStatus;
import com.example.supportservice.domain.member.repository.MemberRepository;
import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.organization.repository.OrganizationRepository;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import com.example.supportservice.domain.subscribe.model.SubscribeStatus;
import com.example.supportservice.domain.subscribe.repository.SubscribeRepository;
import com.example.supportservice.dto.SubscribeDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.CardService;
import com.example.supportservice.service.PaymentService;
import com.example.supportservice.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final MemberRepository memberRepository;
    private final PaymentService paymentService;
    private final OrganizationRepository organizationRepository;
    private final SubscribeRepository subscribeRepository;
    private final CardService cardService;


    @Override
    @Transactional
    public SubscribeDto.Response save(Long memberId,SubscribeDto.Save dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));

        Organization organization = organizationRepository.findById(dto.getOrganizationId())
                .orElseThrow(() -> new AppException((ErrorCode.ORGANIZATION_NOT_FOUND), "기관을 찾을 수 없습니다."));

        if(subscribeRepository.existsByOrganization(organization)) {
            throw new AppException(ErrorCode.SUBSCRIBE_DUPLICATED, "이미 정기후원중인 기관입니다.");
        }

        Subscribe newSubscribe = Subscribe.builder()
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .status(SubscribeStatus.fromString("SUBSCRIBE"))
                .amount(dto.getAmount())
                .member(member)
                .organization(organization)
                .build();

        cardService.save(dto.getCardCompany(),dto.getCardNumber(),member);
        paymentService.save(dto.getCardNumber(), member, dto.getAmount(), dto.getPaymentType(), "SUBSCRIBE", newSubscribe);

        return new SubscribeDto.Response().toDto(subscribeRepository.save(newSubscribe));
    }
    @Override
    @Transactional
    public List<String> updateAllSubscribe() {

        return subscribeRepository.findAll().stream()
                .map(this::updateSubscribe)
                .collect(Collectors.toList());
    }

    private String updateSubscribe(Subscribe subscribe) {

        try {

            Member member = memberRepository.findById(subscribe.getMember().getId())
                    .orElseThrow(() -> new AppException((ErrorCode.MEMBER_NOT_FOUND), "회원을 찾을 수 없습니다."));

            Organization organization = organizationRepository.findById(subscribe.getOrganization().getId())
                    .orElseThrow(() -> new AppException((ErrorCode.ORGANIZATION_NOT_FOUND), "기관을 찾을 수 없습니다."));

            MemberStatus status = member.getStatus();
            LocalDateTime startDate = subscribe.getStartDate();
            LocalDateTime endDate = subscribe.getEndDate();

            if (status == MemberStatus.ACTIVE && endDate.isBefore(LocalDateTime.now())) {
                subscribe.update(startDate, endDate, SubscribeStatus.UNSUBSCRIBE);

                Integer amount = subscribe.getAmount();

                String cardNumber = cardService.cardValidator(member);

                paymentService.save(cardNumber,member, amount,"CARD", "SUBSCRIBE",subscribe);
                subscribe.update(startDate, endDate.plusMonths(1), SubscribeStatus.SUBSCRIBE);

                Long subscribeId = subscribeRepository.save(subscribe).getId();
                return subscribeId + " 정기후원완료";
            }

            return String.format("정기후원ID:%s 실패사유: %s 후원날짜가 아닙니다. ",subscribe.getId(), subscribe.getEndDate());

        } catch (AppException e) {
            Long subscribeId = subscribeRepository.save(subscribe).getId();
            return String.format("정기후원ID:%s 실패사유: %s ",subscribeId, e.getMessage());
        }

    }

}
