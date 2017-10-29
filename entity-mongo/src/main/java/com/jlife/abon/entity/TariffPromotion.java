package com.jlife.abon.entity;

import com.jlife.abon.enumeration.PromotionType;

import java.math.BigDecimal;

/**
 * @author Dzmitry Misiuk
 */
public class TariffPromotion extends AbstractPromotion {

    public TariffPromotion() {
    }

    public TariffPromotion(PromotionType promotionType, BigDecimal promotionValue) {
        super(promotionType);
        this.promotionValue = promotionValue;
    }

    public BigDecimal promotionValue;

    public BigDecimal getPromotionValue() {
        return promotionValue;
    }

    public void setPromotionValue(BigDecimal promotionValue) {
        this.promotionValue = promotionValue;
    }
}
