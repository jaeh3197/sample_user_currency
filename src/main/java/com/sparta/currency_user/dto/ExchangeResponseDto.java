package com.sparta.currency_user.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeResponseDto {

    private Long id;

    private BigDecimal exchange;

    public ExchangeResponseDto(Long id, BigDecimal exchange) {
        this.id = id;
        this.exchange = exchange;
    }
}
