package by.jlife.analytics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Andrei Mozgo
 */
public class AnalyticItem implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime hour;
    private int count;

    public AnalyticItem() {
    }

    public AnalyticItem(DateTime hour, int count) {
        this.hour = hour;
        this.count = count;
    }


    public DateTime getHour() {
        return hour;
    }

    public void setHour(DateTime hour) {
        this.hour = hour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
