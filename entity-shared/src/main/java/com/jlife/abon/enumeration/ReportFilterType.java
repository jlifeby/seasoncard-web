package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum ReportFilterType {

    BY_DAY("Текущий день"),
    BY_MONTH("Текущий месяц"),
    BY_CUSTOM("Выбранный период");

    private String description;

    ReportFilterType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
