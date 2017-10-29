package com.jlife.abon.facade;

import com.jlife.abon.dto.PromotionData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface PromotionFacade {
    List<PromotionData> getAllCompanyPromotions(String companyId);

    List<PromotionData> getPublishedActivePromotions();

    List<PromotionData> getPublishedActualActivePromotionsSortedByNewest(int count);

    List<PromotionData> getActiveActualPromotionsForCompany(String companyId);

    PromotionData getPromotion(String promotionId);

    PromotionData getPromotionAsCompany(String promotionId, String companyId);

    PromotionData createPromotionAsCompany(PromotionData promotion, String companyId);

    List<PromotionData> getPublishedActivePromotionsForCompany(String companyId);

    PromotionData changeActiveAsCompany(String promotionId, String companyId, boolean active);

    PromotionData updatePromotionAsCompany(String promotionId, String companyId, PromotionData promotion);

    Double calculateNewPriceAsCompany(String productId, String promotionId, String companyId);

    List<PromotionData> getPublishedActiveActualPromotionsForCompany(String companyId);

    PromotionData save(PromotionData rowPromotion);
}
