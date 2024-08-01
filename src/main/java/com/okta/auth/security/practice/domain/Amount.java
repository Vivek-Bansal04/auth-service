package com.okta.auth.security.practice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@NoArgsConstructor
public class Amount {

    BigDecimal value;
    Currency instance;
    public Amount(BigDecimal valueOf, Currency instance) {
        this.value = valueOf;
        this.instance = instance;
    }

    public static Amount of(int value, String code){
        return new Amount(BigDecimal.valueOf(value), Currency.getInstance(code));
    }
}