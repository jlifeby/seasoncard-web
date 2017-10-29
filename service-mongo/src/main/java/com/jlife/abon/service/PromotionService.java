package com.jlife.abon.service;

import com.jlife.abon.entity.Product;
import com.jlife.abon.entity.Promotion;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.EntityRepository;
import com.jlife.abon.repository.PromotionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class PromotionService extends AbstractService implements EntityAbstractService<Promotion> {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductService productService;

    public List<Promotion> getPublishedActivePromotionsForCompany(String companyId) {
        return promotionRepository.findByActiveAndPublishedAndCompanyIdIn(true, true, Arrays.asList(companyId));
    }

    public List<Promotion> getPromotionsAsCompany(String companyId) {
        return promotionRepository.findByCompanyId(companyId);
    }

    public List<Promotion> getActiveActualPromotionsAsCompany(String companyId) {
        List<Promotion> termlessPromotions = promotionRepository.findByActiveAndTermlessAndCompanyId(true, true, companyId);
        List<Promotion> actualEndDatePromotions = promotionRepository.findByActiveAndTermlessAndEndDateGreaterThanAndCompanyId(true, false, new DateTime(), companyId);
        List<Promotion> actualPromotions = new ArrayList<>();
        actualPromotions.addAll(termlessPromotions);
        actualPromotions.addAll(actualEndDatePromotions);
        return actualPromotions;
    }


    public Promotion getPromotion(String id) {
        Promotion promotion = promotionRepository.findOne(id);
        if (promotion == null) {
            throw new ResourceNotFoundException(ApiErrorCode.PROMOTION_NOT_FOUND, id);
        }
        return promotion;
    }

    public Promotion getPromotionAsCompany(String promotionId, String companyId) {
        Promotion promotion = getPromotion(promotionId);
        if (!promotion.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_PROMOTION, promotionId);
        }
        return promotion;
    }


    public Promotion updatePromotionAsCompany(String promotionId, String companyId, Promotion newPromotion) {
        Promotion promotion = getPromotionAsCompany(promotionId, companyId);
        promotion.setName(newPromotion.getName());
        promotion.setDescription(newPromotion.getDescription());
        promotion.setPromotionValue(newPromotion.getPromotionValue());
        promotion.setActive(newPromotion.isActive());
        promotion.setTermless(newPromotion.isTermless());
        promotion.setPublished(newPromotion.isPublished());
        promotion.setEndDate(newPromotion.getEndDate());
        promotion.setLogoPath(newPromotion.getLogoPath());
        promotion.setLastModifiedDate(new DateTime());
        return promotionRepository.save(promotion);
    }

    public Promotion createPromotionAsCompany(Promotion promotion, String companyId) {
        promotion.setCompanyId(companyId);
        promotion.setId(null);
        promotion.setCreatedDate(new DateTime());
        promotion.setLastModifiedDate(new DateTime());
        return promotionRepository.save(promotion);
    }

    public Promotion changeActiveForPromotionAsCompany(String promotionId, String companyId, boolean active) {
        Promotion promotion = getPromotionAsCompany(promotionId, companyId);
        promotion.setActive(active);
        return promotionRepository.save(promotion);
    }

    public double calculateNewPrice(String productId, String promotionId, String companyId) {
        Product product = productService.getProduct(productId);
        Promotion promotion = getPromotionAsCompany(promotionId, companyId);
        double originPrice = product.getPrice();
        double newPrice = 0d;
        switch (promotion.getPromotionType()) {
            case PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION:
                BigDecimal price = BigDecimal.valueOf(1223, -2);
                newPrice = (originPrice * (100 - promotion.getPromotionValue()) / 100);
                newPrice = (double) Math.round(newPrice * 100d) / 100d;
                return newPrice;
            case FIXED_DISCOUNT_PRODUCT_PROMOTION:
                newPrice = originPrice - promotion.getPromotionValue();
                newPrice = (double) Math.round(newPrice * 100d) / 100d;
                return newPrice > 0 ? newPrice : 0;
            default:
                // todo unsupported promotions
                throw new NotAllowedException(ApiErrorCode.GENERAL_CODE);
        }
        // todo moving rounding outside switch to support another promotion type
    }

    public List<Promotion> getPublishedActivePromotions() {
        return promotionRepository.findByActiveAndPublished(true, true);
    }

    public List<Promotion> getPublishedActualActivePromotionsSortedByNewestRandom(int count) {
        // it's fake
        // todo right random
        Pageable pageable = new PageRequest(0, 10 * count, new Sort(Sort.Direction.DESC, "createdDate"));
        Page<Promotion> termlessPromotionsPage = promotionRepository.findByActiveAndPublishedAndTermless(true, true, true, pageable);
        Page<Promotion> actualEndDatePromotionsPage = promotionRepository.findByActiveAndPublishedAndTermlessAndEndDateGreaterThan(true, true, false, new DateTime(), pageable);
        List<Promotion> actualPromotions = new ArrayList<>();
        actualPromotions.addAll(termlessPromotionsPage.getContent());
        actualPromotions.addAll(actualEndDatePromotionsPage.getContent());
        if (actualPromotions.isEmpty()) {
            return Collections.emptyList();
        }
        Random random = new Random();
        // multiply to 2 to try generate unique random index
        IntStream ints = random.ints(2 * count, 0, actualPromotions.size());
        List<Promotion> randomPromotions = ints.distinct().limit(count).mapToObj(i -> actualPromotions.get(i)).collect(Collectors.toList());

        return randomPromotions;
    }

    public List<Promotion> getPublishedActiveActualPromotionsForCompany(String companyId) {
        List<Promotion> termlessPromotions = promotionRepository.findByActiveAndPublishedAndTermlessAndCompanyId(true, true, true, companyId);
        List<Promotion> actualEndDatePromotions = promotionRepository.findByActiveAndPublishedAndTermlessAndEndDateGreaterThanAndCompanyId(true, true, false, new DateTime(), companyId);
        List<Promotion> actualPromotions = new ArrayList<>();
        actualPromotions.addAll(termlessPromotions);
        actualPromotions.addAll(actualEndDatePromotions);
        return actualPromotions;
    }

    @Override
    public EntityRepository<Promotion> getBaseRepository() {
        return promotionRepository;
    }

    @Override
    public String getBaseEntityName() {
        return Promotion.class.getSimpleName();
    }
}
