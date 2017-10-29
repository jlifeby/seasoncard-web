package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.PromotionData;
import com.jlife.abon.enumeration.PromotionType;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.PromotionFacade;
import com.jlife.abon.form.PromotionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyPromotionController extends BaseController {

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public PromotionFacade promotionFacade;

    @RequestMapping(value = "/promotions")
    public String getCompanyPromotions(ModelMap model) {
        List<PromotionData> promotions = promotionFacade.getAllCompanyPromotions(getSessionCompanyId());
        model.put("promotions", promotions);
        return "company-promotions";
    }

    @RequestMapping(value = "/promotions/{promotionId}")
    public String getCompanyPromotion(@PathVariable("promotionId") String promotionId,
                                      ModelMap model) {
        PromotionData promotion = promotionFacade.getPromotionAsCompany(promotionId, getSessionCompanyId());
        model.put("promotion", promotion);
        return "company-promotion-view";
    }

    @RequestMapping(value = "/promotions/new")
    public String getNewPromotion(ModelMap model) {
        model.put("promotionForm", new PromotionForm());
        model.put("promotionTypeValues", PromotionType.values());
        return "company-promotion-detail";
    }

    @RequestMapping(value = "/promotions/{promotionId}/detail")
    public String getPromotion(@PathVariable("promotionId") String promotionId,
                               ModelMap model) {
        PromotionData promotion = promotionFacade.getPromotionAsCompany(promotionId, getSessionCompanyId());
        model.put("promotionForm", promotion);
        model.put("promotionTypeValues", PromotionType.values());
        return "company-promotion-detail";
    }

    @RequestMapping(value = "/promotions/save", method = RequestMethod.POST)
    public String savePromotion(ModelMap model,
                                @Valid @ModelAttribute("promotionForm") PromotionForm promotionForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.put("promotionForm", promotionForm);
            model.put("promotionTypeValues", PromotionType.values());
            return "company-promotion-detail";
        }
        PromotionData promotion = promotionForm.toPromotion();

        if (promotion.getId() != null) {
            promotionFacade.updatePromotionAsCompany(promotion.getId(), getSessionCompanyId(), promotion);
            return "redirect:/company/promotions";
        } else {
            try {
                promotion.setCompanyId(getSessionCompanyId());
                promotionFacade.createPromotionAsCompany(promotion, getSessionCompanyId());
                String msg = "Акция успешно создана";
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
                return "redirect:/company/promotions";
            } catch (AbonRuntimeException e) {
                LOG.error("Error on create promotion", e);
                model.put("promotionForm", promotionForm);
                model.put("promotionTypeValues", PromotionType.values());
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
                return "company-promotion-detail";
            }
        }
    }

}
