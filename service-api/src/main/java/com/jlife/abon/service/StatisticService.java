package com.jlife.abon.service;

import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.dto.ProductStatData;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface StatisticService {
    ProductStatData getProductStat(String productId, String companyId);

    int getClientAttendedLastHour(String companyId);

    int getClientAttendedToday(String companyId);

    int countSingleAttendancesToday(String companyId);

    int countSingleAttendancesLastHour(String companyId);

    int getAbonnementSoldCountLastHour(String companyId);

    int getAbonnementSoldCountToday(String companyId);

    double getAbonnementSoldSumLastHour(String companyId);

    double getSingleAttendancesSoldSumLastHour(String companyId);

    double getSingleAttendancesSoldSumToday(String companyId);

    double getAbonnementSoldSumToday(String companyId);

    CurrentStatisticData getCurrentStatistic(String companyId);
}
