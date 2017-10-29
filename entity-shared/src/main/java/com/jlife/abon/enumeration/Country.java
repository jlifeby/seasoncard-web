package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum Country {
    BELARUS("BY", "Беларусь"),
    RUSSIA("RU", "Россия");

    private String localizedName;
    private String isoCode;

    Country(String isoCode, String localizedName) {
        this.isoCode = isoCode;
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public Currency getCurrency() {
        switch (this) {
            case BELARUS:
                return Currency.BYN;
            case RUSSIA:
                return Currency.RUB;
            default:
                return Currency.USD;
        }
    }
}
