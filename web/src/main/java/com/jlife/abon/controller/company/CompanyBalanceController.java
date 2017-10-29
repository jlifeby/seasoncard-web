package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.dto.billing.TransactionData;
import com.jlife.abon.enumeration.PaymentPeriod;
import com.jlife.abon.facade.BillingFacade;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.CompanyStatisticFacade;
import com.jlife.abon.facade.TariffFacade;
import com.jlife.abon.service.SCConfigurationService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyBalanceController extends BaseController {

    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private CardFacade cardFacade;

    @Autowired
    private CompanyStatisticFacade companyStatisticFacade;

    @Autowired
    private TariffFacade tariffFacade;

    @Autowired
    private BillingFacade billingFacade;

    @Autowired
    private SCConfigurationService scConfigurationService;

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public String getBalance(ModelMap model,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        //TODO Update company in session
        getApplication().updateCompanyInSession();

        CompanyData company = getApplication().getCurrentCompany();
        model.put("company", company);
        model.put("countUniqueClientsInPreviousMonth", companyStatisticFacade.countUniqueClientsInPreviousMonth(getSessionCompanyId()));
        DateTime previousMonth = new DateTime(DateTimeZone.forID("Europe/Minsk")).minusMonths(1);
        model.put("previousMonthName", previousMonth.toString("MMMM", new Locale("ru")));

        List<TariffData> tariffs = tariffFacade.getActivePaidTariffs();
        for (TariffData tariff : tariffs) {
            tariff.setLocalTotalPrice(tariff.getLocalizedMonthPrice().get(company.getCountry()));
        }

        model.put("tariffs", tariffs);
        model.put("paymentPeriods", PaymentPeriod.values());
        model.put("smsPrice", scConfigurationService.getSmsPrice(company.getCountry()));

        model.put("account", billingFacade.getAccountByCompanyId(getSessionCompanyId()));

        Pageable pageable = new PageRequest(page - 1, size, new Sort(new Sort.Order(Sort.Direction.DESC, "createdDate")));
        Page<TransactionData> transactionPage = billingFacade.findTransactions(getSessionCompanyId(), pageable);
        model.put("transactions", transactionPage);

        return "company-balance";
    }

    @RequestMapping(value = "/tariff/prolongation", method = RequestMethod.POST)
    public String tariffProlongation(@ModelAttribute("companyId") String companyId,
                                     @ModelAttribute("countOfMonth") int countOfMonth,
                                     @ModelAttribute("tariffId") String tariffId,
                                     ModelMap model,
                                     RedirectAttributes redirectAttrs) {
        try {
            companyFacade.setCurrentTariffAsCompany(countOfMonth, tariffId, getSessionCompanyId());
            getApplication().updateCompanyInSession();
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, "Тариф продлен успешно!");
        } catch (Exception e){
            LOG.error("Error on prolongation tariff", e);
            redirectAttrs.addFlashAttribute(ERROR_MESSAGE, "Ошибка продления тарифа, попробуйте снова");
        }
        return "redirect:/company/balance";
    }

}
