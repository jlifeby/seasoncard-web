package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum PromotionSort {

    NAME("По названию", "name");

    private String description;
    private String fieldName;

    PromotionSort(String description, String fieldName) {
        this.description = description;
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public String getFieldName() {
        return fieldName;
    }
}
