package com.jlife.abon.facade;

import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.dto.statistic.CompanyStatisticData;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

/**
 * Facade for company statistic.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyStatisticFacade {

    DateTimeZone MINSK_TIME_ZONE = DateTimeZone.forID("Europe/Minsk");

    /**
     * Returns count of unique clients for company.
     * Unique means: has at least one attendance or bought at least one abonnement.
     *
     * @param companyId
     * @return
     */
    long countUniqueClientsInPreviousMonth(String companyId);


    /**
     * Returns count of unique clients for company.
     * Unique means: has at least one attendance or bought at least one abonnement.
     *
     * @param companyId
     * @return
     */
    long countUniqueClientsBetween(String companyId, DateTime startDate, DateTime endDate);

    /**
     * Returns statistic for last hour and today
     *
     * @param companyId
     * @return
     */
    CurrentStatisticData getCurrentStatistic(String companyId);


    List<CompanyStatisticData> getAllCompanyStatisticDatas();
}
