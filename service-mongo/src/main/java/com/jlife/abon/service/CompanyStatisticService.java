package com.jlife.abon.service;

import org.joda.time.DateTime;

/**
 * Service to work with statistic of company.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyStatisticService {

    /**
     * Returns count of unique clients for company.
     * Unique means: has at least one attendance or bought at least one abonnement.
     *
     * @param companyId
     * @param endDate
     * @param startDate
     * @return
     */
    long countUniqueClientsBetween(String companyId, DateTime startDate, DateTime endDate);
}
