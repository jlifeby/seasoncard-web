package com.jlife.abon.dto.billing;

import com.jlife.abon.TransactionType;
import com.jlife.abon.dto.BaseData;
import com.jlife.abon.enumeration.Currency;

import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class TransactionData  extends BaseData{

    private String accountId;
    private TransactionType type;
    private Currency currency;
    private BigDecimal value;

    private TransactionDetailsData details;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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

    public TransactionDetailsData getDetails() {
        return details;
    }

    public void setDetails(TransactionDetailsData details) {
        this.details = details;
    }
}
