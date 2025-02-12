package com.sparta.currency_user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "user_currency")
public class UserCurrency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    private BigDecimal amountInKrw;

    private BigDecimal amountAfterExchange;

    private String status;

    public UserCurrency(User user, Currency currency, BigDecimal amountInKrw, BigDecimal amountAfterExchange, String status) {
        this.user = user;
        this.currency = currency;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
    }

    public UserCurrency() {
    }

    public void cancelExchange() {
        this.status = "cancelled";
    }
}
