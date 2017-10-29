package com.jlife.abon.dto;

import com.jlife.abon.enumeration.PromotionType;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class AbstractPromotionData extends BaseData {

    protected PromotionType promotionType;
    private String name;
    private String description;

    public AbstractPromotionData() {
    }

    public AbstractPromotionData(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }
}
