package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class CompanyAccountTest extends ServiceBaseTestCase {

    public static final String BLR_COMPANY = "blr-company";
    @Autowired
    BillingFacade billingFacade;

    @Autowired
    CompanyFacade companyFacade;

    @Test
    public void test_accountWithZeroBalance_whenDoesNotExist() {
        AccountData account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        assertThat(account).isNotNull();

        BigDecimal balance = account.getBalance();
        assertThat(balance).isEqualTo(BigDecimal.ZERO);
        assertThat(account.getCurrency()).isEqualTo(Currency.RUB);
    }

    @Test
    public void test_accountWithTenBalance_whenExists() {
        AccountData rawAccount = new AccountData();
        rawAccount.setBalance(BigDecimal.TEN);
        rawAccount.setCompanyId(DEFAULT_COMPANY_ID);
        rawAccount.setCurrency(Currency.RUB);
        AccountData storedData = billingFacade.saveRaw(rawAccount);

        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        BigDecimal balance = accountData.getBalance();
        assertThat(balance).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void test_currencyBYN_whenBelarusianCompany() {
        CompanyData belarusianCompany = new CompanyData();
        belarusianCompany.setId(BLR_COMPANY);
        belarusianCompany.setCountry(Country.BELARUS);
        belarusianCompany.setContractId(1);
        companyFacade.saveRaw(belarusianCompany);


        AccountData account = billingFacade.getAccountByCompanyId(BLR_COMPANY);
        assertThat(account).isNotNull();

        BigDecimal balance = account.getBalance();
        assertThat(balance).isEqualTo(BigDecimal.ZERO);
        assertThat(account.getCurrency()).isEqualTo(Currency.BYN);
    }
}
