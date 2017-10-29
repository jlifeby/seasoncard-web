package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum PaymentPeriod {

    ONE_MONTH("1 месяц", 1),
    THREE_MONTHS("3 месяц", 3),
    SIX_MONTHS("6 месяцев", 6),
    TWELVE_MONTHS("12 месяцев", 12);

    private String description;
    private int periodValue;

    PaymentPeriod(String description, int periodValue) {
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
