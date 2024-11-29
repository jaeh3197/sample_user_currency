package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.UserExchangeDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.exception.CustomException;
import com.sparta.currency_user.exception.ErrorCode;
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

    /**
     * 환전 요청 로직
     *
     * @param currencyId         환전 대상 통화 식별자
     * @param exchangeRequestDto 환전 요청 Dto
     * @return 환전 결과
     */
    @Transactional
    public ExchangeResponseDto exchange(Long currencyId, ExchangeRequestDto exchangeRequestDto) {

        //환전 요청 Dto 에서 고객 식별자 조회
        Long userId = exchangeRequestDto.getUserId();

        //환전 요청 Dto 에서 환전 전 금액 조회
        BigDecimal amountInKrw = exchangeRequestDto.getAmountInKrw();

        //화전 대상 통화 식별자로 통화 조회
        Currency findCurrency = currencyRepository.findById(currencyId).orElseThrow(
                () -> new CustomException(ErrorCode.CURRENCY_NOT_FOUND));

        //고객 식별자로 고객 조회
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //조회한 통화에서 환율 조회
        BigDecimal exchangeRate = findCurrency.getExchangeRate();

        //환전 후 금액 계산
        BigDecimal amountAfterExchange = amountInKrw.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);

        //새로운 UserCurrency 객체 생성
        UserCurrency userCurrency = new UserCurrency(
                findUser,
                findCurrency,
                amountInKrw,
                amountAfterExchange,
                "normal"
        );

        //repository 에 저장
        UserCurrency savedUserCurrency = userCurrencyRepository.save(userCurrency);

        return new ExchangeResponseDto(savedUserCurrency);
    }

    /**
     * 환전 조회 로직
     *
     * @param userId 고객 고유 식별자
     * @return 조회 결과 목록
     */
    public List<ExchangeResponseDto> findAll(Long userId) {

        //고객 식별자로 환전 요청 조회
        List<UserCurrency> findExchange = userCurrencyRepository.findByUserId(userId);

        //조회 결과 목록으로 반환
        return findExchange.stream().map(ExchangeResponseDto::toDto).toList();
    }

    /**
     * 환전 취소 로직
     *
     * @param id 환전 요청 고유 식별자
     * @return 환전 취소 결과
     */
    @Transactional
    public ExchangeResponseDto cancelExchange(Long id) {

        //환전 요청 식별자로 조회
        UserCurrency findExchange = userCurrencyRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.EXCHANGE_NOT_FOUND));

        //환전 상태 변경
        findExchange.cancelExchange();

        return new ExchangeResponseDto(findExchange);
    }

    /**
     * 환전 그룹 조회 로직
     *
     * @param userId 고객 고유 식별자
     * @return 환전 그룹 조회 결과
     */
    public List<UserExchangeDto> findUserCurrencies(Long userId) {

        return userCurrencyRepository.findUserCurrencies(userId);
    }
}
