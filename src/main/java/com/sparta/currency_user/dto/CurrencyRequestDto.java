package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 10, message = "10글자 이내로 입력해주세요")
    private String currencyName;

    @NotBlank(message = "환율을 입력해주세요")
    private BigDecimal exchangeRate;

    @NotBlank(message = "단위를 입력해주세요")
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyName,
                this.exchangeRate,
                this.symbol
        );
    }
}
