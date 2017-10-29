package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum PromotionType {

    PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION("Относительная (в %)"),
    FIXED_DISCOUNT_PRODUCT_PROMOTION("Фиксированная (в руб.)");

    private String description;

    PromotionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
