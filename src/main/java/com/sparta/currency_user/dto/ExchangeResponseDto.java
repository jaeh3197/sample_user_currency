package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.UserCurrency;
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

    public static ExchangeResponseDto toDto(UserCurrency userCurrency) {
        return new ExchangeResponseDto(
                userCurrency.getId(),
                userCurrency.getAmountAfterExchange()
        );
    }
}
