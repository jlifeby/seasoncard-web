package com.jlife.abon.enumeration;

/**
 * @author Dzmitry Misiuk
 */
public enum ProductSort {

    POPULAR("По популярности", "selling"),
    NAME("По названию", "name");
//    PRICE("По цене", "price");

    private String description;
    private String fieldName;

    ProductSort(String description, String fieldName) {
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
