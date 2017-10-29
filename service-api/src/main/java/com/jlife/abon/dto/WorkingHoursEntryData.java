package com.jlife.abon.dto;

import com.jlife.abon.enumeration.Day;

/**
 * @author Dzmitry Misiuk
 */
public class WorkingHoursEntryData extends IdData {

    private Day day;
    private String open;
    private String close;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }
}
