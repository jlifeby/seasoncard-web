package com.jlife.abon.dto.billing;

import com.jlife.abon.dto.BaseData;
import com.jlife.abon.enumeration.Currency;

import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class AccountData extends BaseData {

    private BigDecimal balance;
    private Currency currency;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
