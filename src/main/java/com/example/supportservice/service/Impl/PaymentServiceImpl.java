package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.payment.model.PaymentItem;
import com.example.supportservice.domain.payment.model.PaymentType;
import com.example.supportservice.domain.payment.model.Payment;
import com.example.supportservice.domain.payment.repository.PaymentRepository;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import com.example.supportservice.dto.PaymentDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaymentDto.Response save(String cardNumber , Member member, Integer amount, String type, String item, Subscribe subscribe) throws AppException{

        payValidator(cardNumber);

        Payment newPayment = Payment.builder()
                .member(member)
                .amount(amount)
                .type(PaymentType.fromString(type))
                .item(PaymentItem.fromString(item))
                .paymentDate(LocalDateTime.now())
                .build();

         if (PaymentItem.fromString(item) == PaymentItem.SUBSCRIBE) newPayment.setSubscribe(subscribe);

        return new PaymentDto.Response().toDto(paymentRepository.save(newPayment));
    }

    private void payValidator(String cardNumber) throws AppException {

        //PG사
        if (cardNumber == null || cardNumber.length() != 16){
            throw new AppException((ErrorCode.PAYMENT_CONFLICT),"카드 정보를 확인해주세요");
        }
    }
}
