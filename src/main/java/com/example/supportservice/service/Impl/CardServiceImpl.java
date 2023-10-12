package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.card.model.Card;
import com.example.supportservice.domain.card.model.CardCompany;
import com.example.supportservice.domain.card.repository.CardRepository;
import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {


    private final CardRepository cardRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void save(String cardCompany, String cardNumber, Member member) {

        if (!cardRepository.existsByMember(member)) {

            Card newCard = Card.builder()
                    .cardCompany(CardCompany.valueOf(cardCompany))
                    .cardNumber(encoder.encode(cardNumber))
                    .member(member)
                    .build();

            cardRepository.save(newCard);

        }
    }

    @Override
    @Transactional
    public String cardValidator(Member member) throws AppException{

        //서비스단에서 카드 정보 확인
        Card card = cardRepository.findByMember(member)
                .orElseThrow(() -> new AppException((ErrorCode.CARD_NOT_FOUND), "등록된 카드가 없습니다."));

        return "*".repeat(16);

    }
}
