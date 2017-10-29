package com.jlife.abon.controller.admin;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.*;
import com.jlife.abon.dto.statistic.CompanyStatisticData;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.PreferredStartingDate;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.*;
import com.jlife.abon.form.CompanyAdminForm;
import com.jlife.abon.form.CompanyProfileForm;
import com.jlife.abon.form.CompanyRequisitesForm;
import com.jlife.abon.form.ProductForm;
import com.jlife.abon.service.StatisticService;
import org.joda.time.DateTimeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliaksei Pashkouski
 * @author Dzmitry Misiuk
 */

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminCompanyController extends BaseController {

    public enum CompanySort {

        IN_ORDER("По порядку"),
        ACTIVE_CLIENT("По активным клиентам"),
        END_TARIFF_DATE("По дате окончания тарифа");

        private String description;

        CompanySort(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }

    private final static Logger logger = LoggerFactory.getLogger(AdminCompanyController.class);

    @Autowired
    public CardFacade cardFacade;

    @Autowired
    private AdminFacade adminFacade;

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public ProductFacade productFacade;

    @Autowired
    public TariffFacade tariffFacade;

    @Autowired
    public StatisticService statisticService;

    @Autowired
    public CompanyStatisticFacade companyStatisticFacade;

    @RequestMapping(value = "/companies")
    public String getAllCompanies(ModelMap model) {
        List<CompanyData> allCompanies = companyFacade.getAllCompaniesWithProducts();
        model.put("companies", allCompanies);
        return "admin-companies";
    }

    @RequestMapping(value = "/companies/full-statistics")
    public String getFullCompaniesStatistics(ModelMap model,
                                             @RequestParam(value = "sort", required = false, defaultValue = "IN_ORDER") CompanySort companySort) {
        List<CompanyStatisticData> allCompanies = companyStatisticFacade.getAllCompanyStatisticDatas();

        if (CompanySort.ACTIVE_CLIENT.equals(companySort)) {
            allCompanies = allCompanies.stream()
                    .sorted((o1, o2) -> -Long.compare(o1.getCountOfActiveClients(), o2.getCountOfActiveClients())).collect(Collectors.toList());
        } else if (CompanySort.END_TARIFF_DATE.equals(companySort)) {
            DateTimeComparator dtComp = DateTimeComparator.getDateOnlyInstance();
            allCompanies = allCompanies.stream()
                    .sorted((o1, o2) -> -dtComp.compare(o1.getCurrentTariff().getEndDate(), o2.getCurrentTariff().getEndDate())).distinct().collect(Collectors.toList());
        }

        model.put("companies", allCompanies);
        model.put("companySorts", CompanySort.values());
        model.put("selectedCompanySort", companySort);

        return "admin-companies-full-statistics";
    }

    @GetMapping("/companies/contacts")
    public String getCompanyContacts(ModelMap model) {
        List<CompanyData> allCompanies = companyFacade.getAllCompaniesWithAdmins();
        // todo getting with admin contacts
        model.put("companies", allCompanies);
        return "admin-companies-contacts";
    }

    @RequestMapping(path = "/companies/{companyId}")
    public String getCompanyById(ModelMap model, @PathVariable String companyId) {
        List<CardData> deliveredToCompanyCards = cardFacade.getDeliveredToCompanyCards(companyId);
        List<CardData> nfcCards = deliveredToCompanyCards.stream().filter(card -> card.getNfcUUID() != null).collect(Collectors.toList());
        CompanyData company = companyFacade.getCompany(companyId);
        // todo use in future count not getEntities().size()
        int amountOfProductsForCompany = productFacade.getAllProductsForCompany(companyId).size();
        int amountOfClients = cardFacade.getClientCards(companyId).size();
        model.put("amountOfClients", amountOfClients);
        model.put("amountOfProducts", amountOfProductsForCompany);
        model.put("company", company);
        model.put("cards", deliveredToCompanyCards);
        model.put("amountOfFreeCards", deliveredToCompanyCards.stream().filter(CardData::isFree).count());
        model.put("amountOfFreeNfcCards", nfcCards.stream().filter(CardData::isFree).count());
        model.put("amountOfNfcCards", nfcCards.size());
        model.put("amountOfAllCards", deliveredToCompanyCards.size());

        CurrentStatisticData currentStatistic = statisticService.getCurrentStatistic(companyId);
        model.put("currentStatistic", currentStatistic);

        return "admin-company";
    }

    @RequestMapping(value = "/companies/{companyId}/profile")
    public String getCompanyProfile(@PathVariable String companyId,
                                    ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
//        model.put("countUniqueClientsInPreviousMonth", companyStatisticFacade.countUniqueClientsInPreviousMonth(companyId));
//        DateTime previousMonth = new DateTime(DateTimeZone.forID("Europe/Minsk")).minusMonths(1);
//        model.put("previousMonthName", previousMonth.toString("MMMM", new Locale("ru")));
        return "company-profile";
    }

    @RequestMapping(path = "/companies/new")
    public String getNewCompany(ModelMap model) {
        model.put("companyProfileForm", new CompanyProfileForm());
        model.put("countries", Country.values());
        return "company-contact-details";
    }

    @RequestMapping(value = "/companies/{companyId}/profile/contact-details")
    public String getCompanyContactDetails(@PathVariable String companyId,
                                           ModelMap model) {
        model.put("companyProfileForm", CompanyProfileForm.fromCompany(companyFacade.getCompany(companyId)));
        model.put("countries", Country.values());
        return "company-contact-details";
    }

    @RequestMapping(value = "/companies/profile/contact-details", method = RequestMethod.POST)
    public String saveCompany(ModelMap model,
                              @Valid @ModelAttribute("companyProfileForm") CompanyProfileForm companyProfileForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.put("companyProfileForm", companyProfileForm);
            return "admin-company-detail";
        }

        CompanyData company = companyProfileForm.toCompany();

        String msg;
        if (company.getId() != null) {
            adminFacade.updateCompany(company.getId(), company);
            msg = String.format("Компания \"%s\" успешно обновлена!", company.getName());
        } else {
            adminFacade.createCompany(company);
            msg = String.format("Компания \"%s\" успешно создана!", company.getName());
        }
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
        return "redirect:/admin/companies/" + company.getId() + "/profile";
    }

    @RequestMapping(value = "/companies/{companyId}/profile/requisites")
    public String getCompanyRequisites(@PathVariable String companyId,
                                       ModelMap model) {
        model.put("companyRequisitesForm", CompanyRequisitesForm.fromCompany(companyFacade.getCompany(companyId)));
        return "company-requisites";
    }

    @RequestMapping(value = "/profile/requisites", method = RequestMethod.POST)
    public String saveCompanyRequisites(ModelMap model,
                                        @Valid @ModelAttribute("companyRequisitesForm") CompanyRequisitesForm companyRequisitesForm,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            model.put("companyRequisitesForm", companyRequisitesForm);
            return "company-requisites";
        }
        try {
            String companyId = companyRequisitesForm.getCompanyId();
            CompanyRequisitesData companyRequisites = companyRequisitesForm.toCompanyRequisites();
            adminFacade.updateCompanyRequisites(companyId, companyRequisites);
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Реквизиты организации обновлены");
            return "redirect:/admin/companies/" + companyId + "/profile";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving company profile", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-requisites";
        }
    }

//    @RequestMapping(value = "/companies/{companyId}/profile/tariff")
//    public String getCompanyTariff(@PathVariable String companyId,
//                                   ModelMap model) {
//        CompanyData company = companyFacade.getCompany(companyId);
//        model.put("company", company);
//        model.put("tariff", company.getCurrentTariff());
//        return "admin-company-tariff-detail";
//    }
//
//    @RequestMapping(value = "/companies/{companyId}/profile/tariff", method = RequestMethod.POST)
//    public String saveCompanyTariff(ModelMap model,
//                                    @PathVariable String companyId,
//                                    @ModelAttribute("tariffForm") TariffForm tariffForm,
//                                    RedirectAttributes redirectAttrs) {
//        try {
//            TariffData currentTariff = companyFacade.getCompany(companyId).getCurrentTariff();
//            currentTariff.setStartDate(tariffForm.getStartDate());
//            currentTariff.setEndDate(tariffForm.getEndDate());
//            tariffFacade.setCurrentTariff(currentTariff, companyId);
//            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Тариф изменен!");
//            return "redirect:/admin/companies/" + companyId + "/balance";
//        } catch (AbonRuntimeException e) {
//            LOG.error("Error on saving company tariff", e);
//            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
//            return "admin-company-tariff-detail";
//        }
//    }

    @RequestMapping(value = "/companies/{companyId}/cards")
    public String addCardsToCompany(@PathVariable("companyId") String companyId,
                                    ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        return "admin-company-add-cards";
    }

    @RequestMapping(value = "/companies/{companyId}/cards/nfc", method = RequestMethod.POST)
    public String addNfcCardsToCompany(@PathVariable("companyId") String companyId,
                                       @ModelAttribute("fromCard") Long fromCard,
                                       @ModelAttribute("toCard") Long toCard,
                                       RedirectAttributes redirectAttributes) {
        if (fromCard > 0 && toCard > 0 && fromCard <= toCard) {
            List<Long> cards = adminFacade.addNfcCardsToCompany(companyId, fromCard, toCard);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Карты успешно добавлены! Количество: " + cards.size());
            return "redirect:/admin/companies/" + companyId;
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Неверный диапазон карт!");
            return "redirect:/admin/companies/" + companyId + "/cards";
        }
    }

    @RequestMapping(value = "/companies/{companyId}/cards/nfc/remove", method = RequestMethod.POST)
    public String removeNfcCardsFromCompany(@PathVariable("companyId") String companyId,
                                            @ModelAttribute("fromCard") Long fromCard,
                                            @ModelAttribute("toCard") Long toCard,
                                            RedirectAttributes redirectAttributes) {
        if (fromCard > 0 && toCard > 0 && fromCard <= toCard) {
            List<Long> cards = adminFacade.removeFreeNfcCardsFromCompany(companyId, fromCard, toCard);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Карты успешно удалены! Количество: " + cards.size());
            return "redirect:/admin/companies/" + companyId;
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Неверный диапазон карт!");
            return "redirect:/admin/companies/" + companyId + "/cards";
        }
    }

    @RequestMapping(value = "/companies/{companyId}/cards/virtual", method = RequestMethod.POST)
    public String addVirtualCardsToCompany(@PathVariable("companyId") String companyId,
                                           @ModelAttribute("count") Integer count,
                                           RedirectAttributes redirectAttributes) {
        if (count > 0) {
            List<Long> cards = adminFacade.addVirtualCardsToCompany(companyId, count);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Карты успешно добавлены! Количество: " + cards.size());
            return "redirect:/admin/companies/" + companyId;
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Неверное количество карт!");
            return "redirect:/admin/companies/" + companyId + "/cards";
        }
    }

    @RequestMapping(value = "/companies/{companyId}/cards/virtual/remove", method = RequestMethod.POST)
    public String removeVirtualCardsFromCompany(@PathVariable("companyId") String companyId,
                                                @ModelAttribute("fromCard") Long fromCard,
                                                @ModelAttribute("toCard") Long toCard,
                                                RedirectAttributes redirectAttributes) {
        if (fromCard > 0 && toCard > 0 && fromCard <= toCard) {
            List<Long> cards = adminFacade.removeFreeVirtualCardsFromCompany(companyId, fromCard, toCard);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Карты успешно удалены! Количество: " + cards.size());
            return "redirect:/admin/companies/" + companyId;
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Неверный диапазон карт!");
            return "redirect:/admin/companies/" + companyId + "/cards";
        }
    }

    @RequestMapping(value = "/companies/{companyId}/admins")
    public String getCompanyAdmins(@PathVariable("companyId") String companyId,
                                   ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        model.put("users", adminFacade.getCompanyAdmins(companyId));
        return "admin-company-admins";
    }

    @RequestMapping(value = "/companies/{companyId}/admins/new")
    public String getNewCompanyAdmin(@PathVariable("companyId") String companyId,
                                     ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        model.put("companyAdminForm", new CompanyAdminForm());
        return "admin-company-admin-detail";
    }

    @RequestMapping(value = "/companies/{companyId}/admins/{userId}")
    public String getCompanyAdmin(@PathVariable("companyId") String companyId,
                                  @PathVariable("userId") String userId,
                                  ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        model.put("companyAdminForm", CompanyAdminForm.fromUser(adminFacade.getCompanyAdmin(companyId, userId)));
        return "admin-company-admin-detail";
    }

    @RequestMapping(value = "/companies/{companyId}/admins/save", method = RequestMethod.POST)
    public String saveUser(ModelMap model,
                           @PathVariable("companyId") String companyId,
                           @Valid @ModelAttribute("companyAdminForm") CompanyAdminForm companyAdminForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            model.put("companyAdminForm", companyAdminForm);
            model.put("company", companyFacade.getCompany(companyId));
            return "admin-company-admin-detail";
        }

        UserData user = companyAdminForm.toUser();

        String msg;
        if (user.getId() == null) {
            try {
                adminFacade.addCompanyAdmin(companyId, user);
                msg = "Администратор компании успешно создан!";
            } catch (AbonRuntimeException e) {
                LOG.error("Error on add new admin", e);
                model.put("companyAdminForm", companyAdminForm);
                model.put("company", companyFacade.getCompany(companyId));
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
                return "admin-company-admin-detail";
            }
        } else {
            adminFacade.updateCompanyAdmin(companyId, user.getId(), user);
            msg = "Администратор компании успешно обновлен!";
        }

        redirectAttrs.addFlashAttribute(FLASH_MESSAGE, msg);
        return "redirect:/admin/companies/" + companyId + "/admins";
    }

    @RequestMapping(value = "/companies/{companyId}/admins/{userId}/send-admin-info")
    public String sendCompanyAdminInfo(@PathVariable("companyId") String companyId,
                                       @PathVariable("userId") String userId,
                                       RedirectAttributes redirectAttrs) {
        adminFacade.sendCompanyAdminCredentials(companyId, userId);
        redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Данные администратора были успешно отправлены на Email!");
        return "redirect:/admin/companies/" + companyId + "/admins/" + userId;
    }

    @RequestMapping(value = "/companies/{companyId}/products")
    public String getCompanyProducts(@PathVariable("companyId") String companyId,
                                     ModelMap model) {
        model.put("company", companyFacade.getCompanyWithActiveProducts(companyId));
        return "admin-company-products";
    }

    @RequestMapping(value = "/companies/{companyId}/products/new")
    public String getNewCompanyProduct(@PathVariable("companyId") String companyId,
                                       ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        model.put("companyProductForm", new ProductForm());
        model.put("preferredStartingDateValues", PreferredStartingDate.values());
        model.put("abonnementTypeValues", AbonnementType.values());
        return "admin-company-product-detail";
    }

    @RequestMapping(value = "/companies/{companyId}/products/{productId}/detail")
    public String getCompanyProduct(@PathVariable("companyId") String companyId,
                                    @PathVariable("productId") String productId,
                                    ModelMap model) {
        model.put("company", companyFacade.getCompany(companyId));
        model.put("companyProductForm", productFacade.getProduct(productId));
        model.put("preferredStartingDateValues", PreferredStartingDate.values());
        model.put("abonnementTypeValues", AbonnementType.values());
        return "admin-company-product-detail";
    }

    @RequestMapping(value = "/companies/{companyId}/products/save", method = RequestMethod.POST)
    public String saveCompanyProduct(ModelMap model,
                                     @PathVariable("companyId") String companyId,
                                     @Valid @ModelAttribute("companyProductForm") ProductForm companyProductForm,
                                     BindingResult result,
                                     RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            model.addAttribute("companyProductForm", companyProductForm);
            model.addAttribute("preferredStartingDateValues", PreferredStartingDate.values());
            model.addAttribute("abonnementTypeValues", AbonnementType.values());
            return "admin-company-product-detail";
        }

        ProductData product = companyProductForm.toProduct();
        try {
            String msg;
            if (product.getId() == null) {
                product.setCompanyId(companyId);
                product.setActive(true);
                productFacade.createProduct(product, companyId);
                msg = String.format("Продукт %s создан!", product.getName());
            } else {
                productFacade.update(product.getId(), product, companyId);
                msg = String.format("Продукт %s сохранен!", product.getName());
            }
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, msg);
            return "redirect:/admin/companies/{companyId}/products";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving product", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("productForm", companyProductForm);
            return "admin-company-product-detail";
        }
    }

    @RequestMapping(value = "/companies/{companyId}/products/archive", method = RequestMethod.GET)
    public String getCompanyArchiveProducts(@PathVariable("companyId") String companyId,
                                            ModelMap model) {
        try {
            model.put("company", companyFacade.getCompany(companyId));
            model.put("products", productFacade.getArchiveProducts(companyId));
        } catch (AbonRuntimeException e) {
            LOG.error("Error on getting archive products", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "admin-company-products-archive";
    }

    @RequestMapping(value = "/companies/{companyId}/products/archive", method = RequestMethod.POST)
    public String archiveProduct(@PathVariable("companyId") String companyId,
                                 @ModelAttribute("productId") String productId,
                                 RedirectAttributes redirectAttributes) {
        try {
            ProductData updatedProduct = productFacade.archiveProduct(productId, companyId);
            redirectAttributes.addFlashAttribute("flashMessage", "Абонемент " + updatedProduct.getName() + " перемещен в архив.");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive product", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/admin/companies/" + companyId + "/products";
    }

    @RequestMapping(value = "/companies/{companyId}/products/{productId}/restore")
    public String restoreProduct(@PathVariable("companyId") String companyId,
                                 @PathVariable("productId") String productId,
                                 RedirectAttributes redirectAttributes) {

        try {
            ProductData updatedProduct = productFacade.restoreProduct(productId, companyId);
            redirectAttributes.addFlashAttribute("flashMessage", "Абонемент " + updatedProduct.getName() + " восстановлен.");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive product", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/admin/companies/" + companyId + "/products";
    }
}
