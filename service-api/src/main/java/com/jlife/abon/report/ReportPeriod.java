package com.jlife.abon.report;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Dzmitry Misiuk
 */
public class ReportPeriod {
    private DateTime startDate;
    private DateTime endDate;

    public ReportPeriod(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public ReportPeriod normalize() {
        final DateTime normalizedStartDate = startOfDayForDate(getStartDate());
        final DateTime normalizedEndDate = endOfDayForDate(getEndDate());
        return new ReportPeriod(normalizedStartDate, normalizedEndDate);
    }

    public ReportPeriod forceUTC() {
        final DateTime startDateWithUTC = getStartDate().withZoneRetainFields(DateTimeZone.UTC);
        final DateTime endDateWithUTC = getEndDate().withZoneRetainFields(DateTimeZone.UTC);
        return new ReportPeriod(startDateWithUTC, endDateWithUTC);
    }

    private DateTime startOfDayForDate(DateTime dateTime) {
        return dateTime.withTimeAtStartOfDay();
    }

    private DateTime endOfDayForDate(DateTime dateTime) {
        // .plusMillis(DateTimeConstants.MILLIS_PER_DAY - 1) may not work when it's time conversion date
        return dateTime.withTimeAtStartOfDay().plusDays(1).minusMillis(1);
    }
}
