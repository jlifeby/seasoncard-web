package com.jlife.abon.controller.rest.company;

import com.jlife.abon.api.NewPriceResponse;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.PromotionData;
import com.jlife.abon.enumeration.PromotionSort;
import com.jlife.abon.facade.PromotionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api/company")
public class PromotionCompanyRestController extends BaseController {

    @Autowired
    PromotionFacade promotionFacade;

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public List<PromotionData> getActivePromotions(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                                   @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sorting = new Sort(new Sort.Order(direction, PromotionSort.NAME.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);
        // todo use pagination in future
        String companyId = getSessionCompanyId();
        return promotionFacade.getAllCompanyPromotions(companyId);
    }

    @RequestMapping(value = "/promotions/{promotionId}", method = RequestMethod.GET)
    public PromotionData getProduct(@PathVariable("promotionId") String promotionId) {
        return promotionFacade.getPromotionAsCompany(promotionId, getSessionCompanyId());
    }


    @RequestMapping(value = "/promotions/new", method = RequestMethod.POST)
    public PromotionData createPromotion(@RequestBody PromotionData promotion) {
        promotion.setId(null);
        promotion.setActive(true);
        promotion.setCompanyId(getSessionCompanyId());
        return promotionFacade.createPromotionAsCompany(promotion, getSessionCompanyId());
    }

    @RequestMapping(value = "/promotions/{promotionId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PromotionData updatePromotion(@RequestBody PromotionData promotion, @PathVariable("promotionId") String promotionId) {
        return promotionFacade.updatePromotionAsCompany(promotionId, getSessionCompanyId(), promotion);
    }

    @RequestMapping(value = "/promotions/calculate-new-price", method = RequestMethod.GET)
    public NewPriceResponse getActivePromotions(@RequestParam(value = "productId", required = true) String productId,
                                                @RequestParam(value = "promotionId", required = true) String promotionId) {
        Double newPrice = promotionFacade.calculateNewPriceAsCompany(productId, promotionId, getSessionCompanyId());

        NewPriceResponse r = new NewPriceResponse();
        r.setNewPrice(newPrice);
        r.setSuccess(true);
        return r;
    }

}
