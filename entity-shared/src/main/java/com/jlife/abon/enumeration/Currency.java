package com.jlife.abon.enumeration;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public enum Currency {
    BYN("руб."),
    RUB("руб."),
    USD("$");

    String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
