package com.sparta.currency_user.repository;

import com.sparta.currency_user.entity.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCurrencyRepository extends JpaRepository<UserCurrency, Long> {
}
