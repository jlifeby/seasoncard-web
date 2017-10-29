package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.TariffData;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TariffFacade {
    TariffData createTariff(TariffData tariff);

    TariffData getTariff(String id);

    TariffData updateTariff(TariffData tariff);

    List<TariffData> getAllTariffs();

    /**
     * @deprecated please use {@link #setCurrentTariff(TariffData, String)}}
     */
    @Deprecated
    CompanyData setCurrentTariff(String companyId, String tariffId, Double totalPrice, DateTime startDate, DateTime endDate, boolean trial, String comment, boolean free);

    CompanyData setCurrentTariff(TariffData tariffData, String companyId);

    TariffData calculateTariff(int countOfMonth, String tariffId, String companyId);

    void removeProduct(TariffData tariff);

    List<TariffData> getActiveTariffs();

    List<TariffData> getActivePaidTariffs();

}
