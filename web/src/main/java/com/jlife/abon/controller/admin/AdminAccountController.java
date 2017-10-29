package com.jlife.abon.controller.admin;

import com.jlife.abon.TransactionType;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData;
import com.jlife.abon.dto.billing.TransactionData;
import com.jlife.abon.enumeration.PaymentPeriod;
import com.jlife.abon.facade.BillingFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.CompanyStatisticFacade;
import com.jlife.abon.facade.TariffFacade;
import com.jlife.abon.service.SCConfigurationService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

/**
 * @author Aliaksei Pashkouski
 * @author Dzmitry Misiuk
 */

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminAccountController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public TariffFacade tariffFacade;

    @Autowired
    public CompanyStatisticFacade companyStatisticFacade;

    @Autowired
    public BillingFacade billingFacade;

    @Autowired
    private SCConfigurationService scConfigurationService;

    @RequestMapping(value = "/companies/{companyId}/balance", method = RequestMethod.GET)
    public String registrationBusiness(@PathVariable String companyId,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                       ModelMap model) {
        CompanyData company = companyFacade.getCompany(companyId);
        model.put("company", company);
        model.put("countUniqueClientsInPreviousMonth", companyStatisticFacade.countUniqueClientsInPreviousMonth(companyId));
        DateTime previousMonth = new DateTime(DateTimeZone.forID("Europe/Minsk")).minusMonths(1);
        model.put("previousMonthName", previousMonth.toString("MMMM", new Locale("ru")));

        List<TariffData> tariffs = tariffFacade.getActivePaidTariffs();
        for (TariffData tariff : tariffs) {
            tariff.setLocalTotalPrice(tariff.getLocalizedMonthPrice().get(company.getCountry()));
        }

        model.put("tariffs", tariffs);
        model.put("paymentPeriods", PaymentPeriod.values());
        model.put("smsPrice", scConfigurationService.getSmsPrice(company.getCountry()));

        model.put("account", billingFacade.getAccountByCompanyId(companyId));

        Pageable pageable = new PageRequest(page - 1, size, new Sort(new Sort.Order(Sort.Direction.DESC, "createdDate")));
        Page<TransactionData> transactionPage = billingFacade.findTransactions(companyId, pageable);
        model.put("transactions", transactionPage);

        return "company-balance";
    }

    @RequestMapping(value = "/companies/{companyId}/tariff/prolongation", method = RequestMethod.POST)
    public String tariffProlongation(@PathVariable String companyId,
                                     @ModelAttribute("countOfMonth") int countOfMonth,
                                     @ModelAttribute("tariffId") String tariffId,
                                     @ModelAttribute("changedPrice") double changedPrice,
                                     @ModelAttribute("startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") DateTime startDate,
                                     @ModelAttribute("endDate") @DateTimeFormat(pattern = "dd.MM.yyyy") DateTime endDate,
                                     ModelMap model,
                                     RedirectAttributes redirectAttrs) {
        try {
            TariffData tariffData = tariffFacade.calculateTariff(countOfMonth, tariffId, companyId);
            if(changedPrice > 0){
                tariffData.getLocalTotalPrice().setValue(new BigDecimal(changedPrice).setScale(2, RoundingMode.HALF_UP));
            }
            tariffData.setStartDate(startDate);
            tariffData.setEndDate(endDate.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59));
            tariffFacade.setCurrentTariff(tariffData, companyId);
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Тариф продлен успешно!");
        } catch (Exception e){
            LOG.error("Error on prolongation tariff", e);
            redirectAttrs.addFlashAttribute(ERROR_MESSAGE, "Ошибка продления тарифа, попробуйте снова");
        }
        return "redirect:/admin/companies/" + companyId + "/balance";
    }

    @RequestMapping(value = "/companies/{companyId}/profile/add-funds")
    public String getCompanyAddFunds(@PathVariable String companyId,
                                     ModelMap model) {
        CompanyData company = companyFacade.getCompany(companyId);
        model.put("company", company);
        return "admin-company-add-funds-detail";
    }

    @RequestMapping(value = "/companies/{companyId}/profile/add-funds", method = RequestMethod.POST)
    public String saveCompanyAddFunds(ModelMap model,
                                      @PathVariable String companyId,
                                      @ModelAttribute("amount") int amount,
                                      @ModelAttribute("comment") String comment,
                                      RedirectAttributes redirectAttrs) {
        if (amount > 0) {

            CompanyData company = companyFacade.getCompany(companyId);
            AccountData account = billingFacade.getAccountByCompanyId(companyId);

            TransactionData transactionData = new TransactionData();
            transactionData.setType(TransactionType.REPLENISHMENT);
            transactionData.setAccountId(account.getId());
            transactionData.setValue(BigDecimal.valueOf(amount));
            transactionData.setCurrency(company.getCurrency());

            ManualReplenishmentDetailsData transactionDetails = new ManualReplenishmentDetailsData();
            transactionDetails.setComment(comment);
            transactionData.setDetails(transactionDetails);

            billingFacade.doReplenishment(transactionData);

            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Деньги зачислены");
            return "redirect:/admin/companies/" + companyId + "/balance";
        } else {
            LOG.error("The amount must be greater than 0");
            model.addAttribute(ERROR_MESSAGE, "Сумма должна быть больше 0");
            return "admin-company-add-funds-detail";
        }
    }


}
