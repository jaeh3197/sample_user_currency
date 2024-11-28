package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.CurrencyRequestDto;
import com.sparta.currency_user.dto.CurrencyResponseDto;
import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.UserCurrencyRepository;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final UserCurrencyRepository userCurrencyRepository;
    private final UserRepository userRepository;

    public CurrencyResponseDto findById(Long id) {
        return new CurrencyResponseDto(findCurrencyById(id));
    }

    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return new CurrencyResponseDto(savedCurrency);
    }

    @Transactional
    public ExchangeResponseDto exchange(Long currencyId, ExchangeRequestDto exchangeRequestDto) {

        Long userId = exchangeRequestDto.getUserId();

        BigDecimal amountInKrw = exchangeRequestDto.getAmountInKrw();

        Currency findCurrency = currencyRepository.findById(currencyId).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        BigDecimal exchangeRate = findCurrency.getExchangeRate();

        BigDecimal amountAfterExchange = amountInKrw.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);

        UserCurrency userCurrency = new UserCurrency(
                findUser,
                findCurrency,
                amountInKrw,
                amountAfterExchange,
                "normal"
        );

        UserCurrency savedUserCurrency = userCurrencyRepository.save(userCurrency);

        return new ExchangeResponseDto(
                savedUserCurrency.getId(),
                savedUserCurrency.getAmountAfterExchange()
        );
    }
}
