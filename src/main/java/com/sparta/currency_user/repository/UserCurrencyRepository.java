package com.sparta.currency_user.repository;

import com.sparta.currency_user.dto.UserExchangeDto;
import com.sparta.currency_user.entity.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCurrencyRepository extends JpaRepository<UserCurrency, Long> {

    //고객 식별자로 조회하여 목록 생성
    List<UserCurrency> findByUserId(Long userId);

    //Query 로 요청 횟수와 전체 요청 금액 조회
    @Query("select new com.sparta.currency_user.dto.UserExchangeDto(count(u.id), sum(u.amountInKrw))" +
            "from UserCurrency u group by u.user")
    List<UserExchangeDto> findUserCurrencies(Long userId);
}
