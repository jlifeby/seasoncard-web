package com.jlife.abon.entity;

import com.jlife.abon.enumeration.PromotionType;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class AbstractPromotion extends Entity {

    protected PromotionType promotionType;
    protected String name;
    protected String description;

    public AbstractPromotion() {
    }

    public AbstractPromotion(PromotionType promotionType) {
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
