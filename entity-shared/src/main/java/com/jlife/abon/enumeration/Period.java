package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum Period {

    DAY("День", 1),
    WEEK("Неделя", 7),
    MONTH("Месяц", 30);

    private String description;
    private int periodValue;

    Period(String description, int periodValue) {
        this.description = description;
        this.periodValue = periodValue;
    }

    public String getDescription() {
        return description;
    }

    public int getPeriodValue() {
        return periodValue;
    }
}
