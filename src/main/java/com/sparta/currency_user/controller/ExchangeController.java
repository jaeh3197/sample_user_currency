package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/{currencyId}")
    public ResponseEntity<ExchangeResponseDto> exchangeCurrency(
            @PathVariable Long currencyId,
            @RequestBody ExchangeRequestDto exchangeRequestDto) {

        return ResponseEntity.ok().body(exchangeService.exchange(currencyId, exchangeRequestDto));
    }
}
