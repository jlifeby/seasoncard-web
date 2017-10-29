package com.jlife.abon.entity;

import com.jlife.abon.enumeration.Currency;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class Price implements Serializable {

    private Currency currency;
    private BigDecimal value;

    public Price() {
    }

    public Price(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}