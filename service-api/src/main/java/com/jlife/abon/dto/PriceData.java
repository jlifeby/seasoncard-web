package com.jlife.abon.dto;

import com.jlife.abon.enumeration.Currency;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class PriceData implements Serializable {

    private Currency currency;
    private BigDecimal value;

    public PriceData() {
    }

    public PriceData(Currency currency, BigDecimal value) {
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
