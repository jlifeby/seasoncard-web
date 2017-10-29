package by.jlife.analytics.dto;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Andrei Mozgo
 */
public class AnalyticsRequest implements Serializable{

    private DateTime startDate;
    private DateTime endDate;

    public AnalyticsRequest() {
        // todo user company TimeZone
        this.endDate = new DateTime();
        this.startDate = new DateTime().withTimeAtStartOfDay();
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
}
