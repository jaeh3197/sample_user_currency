package com.sparta.currency_user.service;

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
public class ExchangeService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final UserCurrencyRepository userCurrencyRepository;

    //환전 요청 수행
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

    public List<ExchangeResponseDto> findAll(Long userId) {

        List<UserCurrency> findExchange = userCurrencyRepository.findByUserId(userId);

        return findExchange.stream().map(ExchangeResponseDto::toDto).toList();
    }
}
