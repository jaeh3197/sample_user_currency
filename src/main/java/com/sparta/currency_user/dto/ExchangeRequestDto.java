package com.sparta.currency_user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeRequestDto {

    @NotBlank(message = "고객 ID를 입력해주세요")
    private Long userId;

    @NotBlank(message = "금액을 입력해주세요")
    private BigDecimal amountInKrw;

    public ExchangeRequestDto(Long userId, BigDecimal amountInKrw) {
        this.userId = userId;
        this.amountInKrw = amountInKrw;
    }
}
