package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * 환전 요청 메서드
     *
     * @param currencyId         환전 대상 통화 식별자
     * @param exchangeRequestDto 환전 요청 dto
     * @return 환전 결과
     */
    @PostMapping("/{currencyId}")
    public ResponseEntity<ExchangeResponseDto> exchangeCurrency(
            @PathVariable Long currencyId,
            @RequestBody ExchangeRequestDto exchangeRequestDto) {

        return ResponseEntity.ok().body(exchangeService.exchange(currencyId, exchangeRequestDto));
    }

    /**
     * 환전 조회 메서드
     *
     * @param userId 고객 고유 식별자
     * @return 조회 결과 목록
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<ExchangeResponseDto>> findExchanges(@PathVariable Long userId) {

        return ResponseEntity.ok().body(exchangeService.findAll(userId));
    }

    /**
     * 환전 취소 메서드
     *
     * @param id 환전 요청 고유 식별자
     * @return 환전 취소 결과
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ExchangeResponseDto> updateExchange(@PathVariable Long id) {

        return ResponseEntity.ok().body(exchangeService.cancelExchange(id));
    }
}
