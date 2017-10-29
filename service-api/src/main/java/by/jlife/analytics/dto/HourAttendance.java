package by.jlife.analytics.dto;

import org.joda.time.DateTime;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Andrei Mozgo
 */
public class HourAttendance extends AnalyticItem {

    public HourAttendance() {
    }

    public HourAttendance(DateTime hour, int count) {
        super(hour, count);
    }
}
