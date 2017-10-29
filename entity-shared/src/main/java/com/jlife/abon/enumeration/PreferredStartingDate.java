package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum PreferredStartingDate {
    PURCHASE_DATE("С даты покупки"),
    MONTH_START("С начала месяца");

    private String description;

    PreferredStartingDate(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
