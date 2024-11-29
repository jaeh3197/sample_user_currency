package com.sparta.currency_user.config;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@Profile("dev")
public class DataInitializer {

    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * 구동시 환율 테이블 생성
     */
    @PostConstruct
    public void init() {

        //환율 객체 생성
        Currency currency1 = new Currency("USD", new BigDecimal("1430.00"), "$");
        Currency currency2 = new Currency("USD", new BigDecimal("1400.00"), "$");
        Currency currency3 = new Currency("USD", new BigDecimal("-1"), "$");

        //repository 에 저장
        currencyRepository.save(currency1);
        currencyRepository.save(currency2);
        currencyRepository.save(currency3);

        //저장된 테이블 검증
        for (Currency currency : currencyRepository.findAll()) {
            int result = currency.getExchangeRate().compareTo(BigDecimal.ZERO);
            if (result <= 0) {
                log.info("Currency exchange rate is lower than zero");
            }
        }
        log.info("===== Test Data Initialized =====");
    }
}
