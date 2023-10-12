package com.example.supportservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {


    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED),

    SUPPORT_CONFLICT(HttpStatus.CONFLICT),

    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND),

    POINT_NOT_FOUND(HttpStatus.NOT_FOUND),
    POINT_REFUNDS_NOT_FOUND(HttpStatus.NOT_FOUND),
    POINT_REFUNDS_CONFLICT(HttpStatus.CONFLICT),

    CARD_NOT_FOUND(HttpStatus.NOT_FOUND),

    PAYMENT_CONFLICT(HttpStatus.CONFLICT),

    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND);


    private HttpStatus httpStatus;
}
