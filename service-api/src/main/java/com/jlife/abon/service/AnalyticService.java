package com.jlife.abon.service;

import by.jlife.analytics.dto.AnalyticItem;
import by.jlife.analytics.dto.HourAttendance;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface AnalyticService {

    List<HourAttendance> getForAttendancesAsCompany(DateTime startDate, DateTime endDate, String companyId);

    List<HourAttendance> getForAttendances(DateTime startDate, DateTime endDate);

    List<AnalyticItem> getForNewClients(@NotNull DateTime startDate, @NotNull DateTime endDate);

    List<AnalyticItem> getForSoldProducts(@NotNull DateTime startDate, @NotNull DateTime endDate);
}
