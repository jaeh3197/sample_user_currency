package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.UserCurrency;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeResponseDto {

    private Long id;

    private BigDecimal exchange;

    private String status;

    public ExchangeResponseDto(UserCurrency userCurrency) {
        this.id = userCurrency.getId();
        this.exchange = userCurrency.getAmountAfterExchange();
        this.status = userCurrency.getStatus();
    }

    public ExchangeResponseDto(Long id, BigDecimal exchange, String status) {
        this.id = id;
        this.exchange = exchange;
        this.status = status;
    }

    public static ExchangeResponseDto toDto(UserCurrency userCurrency) {
        return new ExchangeResponseDto(
                userCurrency.getId(),
                userCurrency.getAmountAfterExchange(),
                userCurrency.getStatus()
        );
    }
}
