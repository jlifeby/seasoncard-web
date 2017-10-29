package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Khralovich
 */
public enum ReportTrainerType {

    BY_SOLD_ABONNEMENTS("Проданные абонементы"),
    BY_SINGLE_ATTENDANCES("Разовые посещения"),
    BY_ATTENDANCES("Посещенные занятия");

    private String description;

    ReportTrainerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
