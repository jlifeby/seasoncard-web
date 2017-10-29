package com.jlife.abon.service;

import com.jlife.abon.dto.PriceData;
import com.jlife.abon.enumeration.Country;

import java.math.BigDecimal;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface SCConfigurationService {

    PriceData getSmsPrice(Country country);

    BigDecimal getInitialAccountBalance(Country country);

    int getDurationForFreeTariffDays();
}
