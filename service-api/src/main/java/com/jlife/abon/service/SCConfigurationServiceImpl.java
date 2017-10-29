package com.jlife.abon.service;

import com.jlife.abon.dto.PriceData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class SCConfigurationServiceImpl implements SCConfigurationService {

    @Value("${price.sms.BELARUS}")
    private String belarusSmsPrice;

    @Value("${price.sms.RUSSIA}")
    private String russiaSmsPrice;

    @Value("${account.balance.initial.BELARUS}")
    private String belarusInitialAccountBalance;

    @Value("${account.balance.initial.RUSSIA}")
    private String russiaInitialAccountBalance;

    @Value("${tariff.free.duration.days}")
    private int durationOfFreeTariffDays;

    @Override
    public PriceData getSmsPrice(Country country) {
        switch (country) {
            case BELARUS:
                return new PriceData(Currency.BYN, new BigDecimal(belarusSmsPrice));
            case RUSSIA:
                return new PriceData(Currency.RUB, new BigDecimal(russiaSmsPrice));
            default:
                throw new RuntimeException("not supported country " + country);
        }
    }

    @Override
    public BigDecimal getInitialAccountBalance(Country country) {
        switch (country) {
            case BELARUS:
                return new BigDecimal(belarusInitialAccountBalance);
            case RUSSIA:
                return new BigDecimal(russiaInitialAccountBalance);
            default:
                throw new RuntimeException("not supported country " + country);
        }
    }

    @Override
    public int getDurationForFreeTariffDays() {
        return durationOfFreeTariffDays;
    }
}
