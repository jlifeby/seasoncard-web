package com.jlife.abon.dto.billing;

import com.jlife.abon.dto.AbstractPromotionData;
import com.jlife.abon.enumeration.PromotionType;

import java.math.BigDecimal;

/**
 * @author Dzmitry Misiuk
 */
public class TariffPromotionData extends AbstractPromotionData {

    public TariffPromotionData(){

    }

    public TariffPromotionData(PromotionType promotionType, BigDecimal promotionValue) {
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
