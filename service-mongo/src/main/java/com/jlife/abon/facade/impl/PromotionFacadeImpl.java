package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.PromotionData;
import com.jlife.abon.entity.Promotion;
import com.jlife.abon.facade.PromotionFacade;
import com.jlife.abon.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class PromotionFacadeImpl extends AbstractFacade implements PromotionFacade {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<PromotionData> getAllCompanyPromotions(String companyId) {
        List<Promotion> promotionsAsCompany = promotionService.getPromotionsAsCompany(companyId);
        return dataMapper.toPromotionDataList(promotionsAsCompany);
    }

    @Override
    public List<PromotionData> getPublishedActivePromotions() {
        List<Promotion> promotions = promotionService.getPublishedActivePromotions();
        populateCompany(promotions);
        return dataMapper.toPromotionDataList(promotions);
    }

    @Override
    public List<PromotionData> getPublishedActualActivePromotionsSortedByNewest(int count) {
        List<Promotion> promotions = promotionService.getPublishedActualActivePromotionsSortedByNewestRandom(count);
        populateCompany(promotions);
        return dataMapper.toPromotionDataList(promotions);
    }

    @Override
    public List<PromotionData> getActiveActualPromotionsForCompany(String companyId) {
        List<Promotion> activeActualPromotionsAsCompany = promotionService.getActiveActualPromotionsAsCompany(companyId);
        return dataMapper.toPromotionDataList(activeActualPromotionsAsCompany);
    }

    @Override
    public PromotionData getPromotion(String promotionId) {
        Promotion promotion = promotionService.getPromotion(promotionId);
        return dataMapper.toPromotionData(promotion);
    }

    @Override
    public PromotionData getPromotionAsCompany(String promotionId, String companyId) {
        Promotion promotionAsCompany = this.promotionService.getPromotionAsCompany(promotionId, companyId);
        return dataMapper.toPromotionData(promotionAsCompany);
    }

    @Override
    public PromotionData createPromotionAsCompany(PromotionData promotionData, String companyId) {
        Promotion promotionAsCompany = promotionService.createPromotionAsCompany(dataMapper.fromPromotionData(promotionData), companyId);
        return dataMapper.toPromotionData(promotionAsCompany);
    }

    @Override
    public List<PromotionData> getPublishedActivePromotionsForCompany(String companyId) {
        List<Promotion> publishedActivePromotionsForCompany = promotionService.getPublishedActivePromotionsForCompany(companyId);
        return dataMapper.toPromotionDataList(publishedActivePromotionsForCompany);
    }

    /**
     * For archiving
     *
     * @param promotionId
     * @param companyId
     * @param active
     * @return
     */
    @Override
    public PromotionData changeActiveAsCompany(String promotionId, String companyId, boolean active) {
        Promotion promotion = promotionService.changeActiveForPromotionAsCompany(promotionId, companyId, active);
        return dataMapper.toPromotionData(promotion);
    }

    @Override
    public PromotionData updatePromotionAsCompany(String promotionId, String companyId, PromotionData promotionData) {
        Promotion promotion = promotionService.updatePromotionAsCompany(promotionId, companyId, dataMapper.fromPromotionData(promotionData));
        return dataMapper.toPromotionData(promotion);
    }

    @Override
    public Double calculateNewPriceAsCompany(String productId, String promotionId, String companyId) {
        return promotionService.calculateNewPrice(productId, promotionId, companyId);
    }

    @Override
    public List<PromotionData> getPublishedActiveActualPromotionsForCompany(String companyId) {
        List<Promotion> promotionList = promotionService.getPublishedActiveActualPromotionsForCompany(companyId);
        return dataMapper.toPromotionDataList(promotionList);
    }

    @Override
    public PromotionData save(PromotionData rowPromotion) {
        Promotion savedPromotion = promotionRepository.save(dataMapper.fromPromotionData(rowPromotion));
        return dataMapper.toPromotionData(savedPromotion);
    }
}
