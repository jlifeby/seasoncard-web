package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.facade.AbonnementFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.CompanySelfRegistrationFacade;
import com.jlife.abon.facade.ProductFacade;
import com.jlife.abon.facade.PromotionFacade;
import com.jlife.abon.form.CompanySelfRegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Khralovich Dzmitry
 */
@Controller
public class MainController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ProductFacade productFacade;


    @Autowired
    private AbonnementFacade abonnementFacade;


    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private PromotionFacade promotionFacade;

    @Autowired
    private CompanySelfRegistrationFacade companySelfRegistrationFacade;

    @RequestMapping(value = "")
    public String getMain(HttpServletRequest request, ModelMap model) {
        if (request.isUserInRole("ROLE_COMPANY")) {
            return "forward:/company";
        } else if (request.isUserInRole("ROLE_USER")) {

            model.put("abons", abonnementFacade.getLastAbonnementsForUser(getCurrentUser().getId()));

            return "user-abons";

        } else {
//            model.put("products", productFacade.getMostSellingPublishedProductsForEachCompany(5));
            model.put("companies", companyFacade.getAllPublishedCompanies());
//            model.put("promotions", promotionFacade.getPublishedActualActivePromotionsSortedByNewest(5));
            model.put("companySelfRegistrationForm", new CompanySelfRegistrationForm());
            model.put("countries", Country.values());
            return "main";
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getProducts(@RequestParam(value = "sort", required = false, defaultValue = "POPULAR") ProductSort productSort,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                              @RequestParam(value = "direction", required = false) Sort.Direction direction,
                              ModelMap model) {
        if (direction == null) {
            if (ProductSort.POPULAR == productSort) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sorting = new Sort(new Sort.Order(direction, productSort.getFieldName()));
        Pageable pageRequest = new PageRequest(page - 1, size, sorting);
        Page<ProductData> productPage = productFacade.getActivePublishedProductsWithCompanies(pageRequest);
        model.put("products", productPage.getContent());

        model.put("productSorts", ProductSort.values());
        model.put("sort", productSort);
        model.put("page", page);
        model.put("totalPages", productPage.getTotalPages());

        return "products";
    }

    @RequestMapping(value = "/products/{productId}")
    public String getProduct(@PathVariable("productId") String productId, ModelMap model) {
        model.put("product", productFacade.getProductWithCompany(productId));
        return "product-view";
    }

//    @RequestMapping(value = "/companies")
//    public String getCompanies(ModelMap model) {
//        model.put("companies", companyFacade.getAllPublishedCompanies());
//        return "companies";
//    }

    @RequestMapping(value = "/companies/{companyId}")
    public String getCompany(@PathVariable("companyId") String companyId, ModelMap model) {
        // todo add sorting/paging
        model.put("company", companyFacade.getCompanyWithActivePublishedProducts(companyId));
        model.put("promotions", promotionFacade.getPublishedActiveActualPromotionsForCompany(companyId));
        return "company-view";
    }

//    @RequestMapping(value = "/company/{companyId}/feedback", method = RequestMethod.POST)
//    public String feedbackOrg(@PathVariable("companyId") String companyId,
//                              @ModelAttribute("name") String name,
//                              @ModelAttribute("email") String email,
//                              @ModelAttribute("message") String message,
//                              final HttpServletRequest request,
//                              RedirectAttributes redirectAttrs) {
//        Company company = companyRepository.findOne(companyId);
//        //TODO send feedback
////        emailService.sendFeedbackToOrganization(organization, name, email, message);
////        String flashMessage = "Ваше письмо отправлено организации " + organization.getName();
////        redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
//        String referrer = request.getHeader("referer");
//        return String.format("redirect:%s", referrer);
//    }

    @RequestMapping("/about")
    public String getAbout(ModelMap model) {
        return "about";
    }

    @RequestMapping("/contacts")
    public String getContacts(ModelMap model) {
        return "contacts";
    }

    @RequestMapping("/features")
    public String getFeatures(ModelMap model) {
        return "features";
    }

    @RequestMapping(value = {"/tariffs", "/tariffs/byn"})
    public String getTariffs(ModelMap model) {
        return "tariffs";
    }

    @RequestMapping("/tariffs/rub")
    public String getTariffsRub(ModelMap model) {
        return "tariffs-rub";
    }

    @RequestMapping("/tariffs/usd")
    public String getTariffsUsd(ModelMap model) {
        return "tariffs-usd";
    }

    @RequestMapping("/apps")
    public String getMobileApp(ModelMap model) {
        return "apps";
    }

    @RequestMapping("/faq")
    public String getFaq(ModelMap model) {
        return "faq";
    }

    @RequestMapping("/video-help")
    public String getVideoHelp(ModelMap model) {
        return "video-help";
    }

//    @RequestMapping("/registration-company")
//    public String getRegistrationCompany(ModelMap model) {
//        model.put("companySelfRegistrationForm", new CompanySelfRegistrationForm());
//        model.put("countries", Country.values());
//        return "registration-company";
//    }
//
//    @RequestMapping(value = "/registration-company", method = RequestMethod.POST)
//    public String postRegistrationCompany(ModelMap model,
//                                          @Valid @ModelAttribute("companySelfRegistrationForm") CompanySelfRegistrationForm companySelfRegistrationForm,
//                                          BindingResult bindingResult,
//                                          RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            model.put("companySelfRegistrationForm", companySelfRegistrationForm);
//            return "registration-company";
//        }
//
//        CompanyData company = companySelfRegistrationForm.toCompany();
//        UserData admin = companySelfRegistrationForm.extractUser();
//
//        String msg;
//        try {
//            companySelfRegistrationFacade.selfRegisterCompany(company, admin);
//            msg = String.format("Компания \"%s\" успешно создана! Данные для входа отправлены на %s", company.getName(), admin.getEmail());
//            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
//            return "redirect:/login";
//        } catch (AbonRuntimeException e) {
//            LOG.error("Error on registration company profile", e);
//            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
//            return "registration-company";
//        }
//    }

}
