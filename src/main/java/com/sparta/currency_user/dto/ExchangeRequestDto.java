package com.sparta.currency_user.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeRequestDto {

    private Long userId;

    private BigDecimal amountInKrw;

    public ExchangeRequestDto(Long userId, BigDecimal amountInKrw) {
        this.userId = userId;
        this.amountInKrw = amountInKrw;
    }
}
