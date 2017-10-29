package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum AbonnementType {

    BY_ATTENDANCE("По посещениям"),
    BY_TIME("Временные"),
    BY_UNIT("По единицам");

    private String description;

    AbonnementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
