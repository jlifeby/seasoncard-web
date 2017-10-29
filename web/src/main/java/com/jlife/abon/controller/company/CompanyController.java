package com.jlife.abon.controller.company;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.CompanyRequisitesData;
import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.CompanyTrainerFacade;
import com.jlife.abon.form.CompanyProfileForm;
import com.jlife.abon.form.CompanyRequisitesForm;
import com.jlife.abon.form.CompanySettingsForm;
import com.jlife.abon.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyController extends BaseController {

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public CardFacade cardFacade;

    @Autowired
    public CompanyTrainerFacade companyTrainerFacade;

    @Autowired
    public StatisticService statisticService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCompanyMain(ModelMap model) {

        CurrentStatisticData currentStatistic = statisticService.getCurrentStatistic(getSessionCompanyId());
        model.put("currentStatistic", currentStatistic);
        model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));

        //TODO Update company in session
        getApplication().updateCompanyInSession();

        return "company-main";
    }

    @RequestMapping(value = "/accept", method = RequestMethod.GET)
    public String registrationBusiness(ModelMap model) {
        model.put("company", getApplication().getCurrentCompany());
        return "registration-business";
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public String registrationBusinessPost(ModelMap model) {
        companyFacade.acceptAgreementAndPublicOffer(getSessionCompanyId());
        getApplication().updateCompanyInSession();
        return "redirect:/";
    }

    @RequestMapping(value = "/profile")
    public String getCompanyProfile(ModelMap model) {
        model.put("company", getApplication().getCurrentCompany());
        return "company-profile";
    }


    @RequestMapping(value = "/profile/contact-details")
    public String getCompanyContactDetails(ModelMap model) {
        model.put("companyProfileForm", CompanyProfileForm.fromCompany(getApplication().getCurrentCompany()));
        model.put("countries", Country.values());
        return "company-contact-details";
    }

    @RequestMapping(value = "/profile/contact-details", method = RequestMethod.POST)
    public String saveCompanyContactDetails(Model model,
                                            @Valid @ModelAttribute("companyProfileForm") CompanyProfileForm companyProfileForm,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "company-contact-details";
        }
        try {
            CompanyData company = companyProfileForm.toCompany();
            companyFacade.updateCompanyOwn(getSessionCompanyId(), company);
            getApplication().updateCompanyInSession();
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Контактные данные сохранены");
            return "redirect:/company/profile";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving company profile", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-contact-details";
        }
    }


    @RequestMapping(value = "/profile/requisites")
    public String getCompanyRequisites(ModelMap model) {
        model.put("companyRequisitesForm", CompanyRequisitesForm.fromCompany(getApplication().getCurrentCompany()));
        return "company-requisites";
    }

    @RequestMapping(value = "/profile/requisites", method = RequestMethod.POST)
    public String saveCompanyRequisites(Model model,
                                        @Valid @ModelAttribute("companyRequisitesForm") CompanyRequisitesForm companyRequisitesForm,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "company-requisites";
        }
        try {
            CompanyRequisitesData companyRequisites = companyRequisitesForm.toCompanyRequisites();
            companyFacade.updateCompanyRequisitesOwn(getSessionCompanyId(), companyRequisites);
            getApplication().updateCompanyInSession();
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Реквизиты организации сохранены");
            return "redirect:/company/profile";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving company requisites", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-requisites";
        }
    }


    @RequestMapping(value = "/info")
    public String getCompanyInfo(ModelMap model) {
        model.put("companyProfileForm", CompanyProfileForm.fromCompany(getApplication().getCurrentCompany()));
        return "company-info";
    }

    @RequestMapping(value = "/profile/cards/free")
    public String getFreeCards(ModelMap model) {
        List<CardData> cards = cardFacade.getDeliveredToCompanyCards(getSessionCompanyId());
        Predicate<CardData> isFree = new Predicate<CardData>() {
            @Override
            public boolean apply(CardData input) {
                return input.isFree();
            }
        };
        int countOfFreeCard = Collections2.filter(cards, isFree).size();
        int countOfAllCard = cards.size();
        model.put("cards", cards);
        model.put("countOfFreeCard", countOfFreeCard);
        model.put("countOfAllCard", countOfAllCard);
        return "company-cards-free";
    }

    @RequestMapping(value = "/settings/product")
    public String getCompanyProductSettings(ModelMap model) {
        return "company-settings-product";
    }

    @RequestMapping(value = "/settings/product", method = RequestMethod.POST)
    public String saveCompanyProductSetting(ModelMap model,
                                            @NotNull @ModelAttribute("logoPath") String logoPath,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return "company-profile";
        }
        try {
            companyFacade.updateCompanyProductLogoPathSettings(logoPath, getSessionCompanyId());
            getApplication().updateCompanyInSession();
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Изображения абонемента по умолчанию обновлено");
            return "redirect:/company/profile";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving company default logo", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-profile";
        }
    }

    @RequestMapping(value = "/settings/notification")
    public String getCompanyNotificationSettings(ModelMap model) {
        model.put("companySettingsForm", new CompanySettingsForm(getApplication().getCurrentCompany().getCompanySettings()));
        return "company-settings-notification";
    }

    @RequestMapping(value = "/settings/notification", method = RequestMethod.POST)
    public String saveCompanyNotificationSettings(ModelMap model,
                                                  @Valid @ModelAttribute("companySettingsForm") CompanySettingsForm companySettingsForm,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "company-settings-notification";
        }
        try {
            companyFacade.updateCompanySettings(getSessionCompanyId(), companySettingsForm.toCompanySettings());
            getApplication().updateCompanyInSession();
            model.put(FLASH_MESSAGE, "Настройки сохранены");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving company notification settings", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }

        return "company-settings-notification";
    }

}
