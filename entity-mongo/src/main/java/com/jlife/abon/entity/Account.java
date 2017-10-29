package com.jlife.abon.entity;

import com.jlife.abon.enumeration.Currency;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Document(collection = "accounts")
@CompoundIndex(unique = true, def = "{'currency' : 1, 'companyId' : 1}")
public class Account extends Entity {

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