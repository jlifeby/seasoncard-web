package com.jlife.abon.facade;

import com.jlife.abon.TransactionType;
import com.jlife.abon.audit.JunitAuditor;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.PriceData;
import com.jlife.abon.dto.SmsMessageData;
import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData;
import com.jlife.abon.dto.billing.TransactionData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import com.jlife.abon.enumeration.SmsType;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.service.SCConfigurationService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class DoingPaymentForDirectSmsTest extends ServiceBaseTestCase {


    @Value("${price.sms.BELARUS}")
    private String belarusSmsPrice;

    @Value("${price.sms.RUSSIA}")
    private String russiaSmsPrice;

    @Autowired
    CompanySmsFacade companySmsFacade;

    @Autowired
    SCConfigurationService scConfigurationService;

    @Autowired
    BillingFacade billingFacade;

    @Autowired
    CompanyFacade companyFacade;

    @Test
    public void test_emptySmsList_whenInitial() {
        Page<SmsMessageData> messagesPage = companySmsFacade
                .findSmsMessagesForCompany(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(messagesPage.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    public void test_smsPriceForBelarus() {
        PriceData belarusSmsPrice = scConfigurationService.getSmsPrice(Country.BELARUS);
        assertThat(belarusSmsPrice).isNotNull();

        assertThat(belarusSmsPrice.getValue()).isEqualTo(new BigDecimal(this.belarusSmsPrice));
        assertThat(belarusSmsPrice.getCurrency()).isEqualTo(Currency.BYN);
    }

    @Test
    public void test_smsPriceForRussia() {
        PriceData russiaSmsPrice = scConfigurationService.getSmsPrice(Country.RUSSIA);
        assertThat(russiaSmsPrice).isNotNull();

        assertThat(russiaSmsPrice.getValue()).isEqualTo(new BigDecimal(this.russiaSmsPrice));
        assertThat(russiaSmsPrice.getCurrency()).isEqualTo(Currency.RUB);
    }

    @Test
    public void test_smsSend_whenDirectSmsAndHasMoneyOnAccount() {
        doManualReplenishment(BigDecimal.TEN);

        companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, "bla-bla", DEFAULT_COMPANY_ID);

        Page<SmsMessageData> messagesPage = companySmsFacade
                .findSmsMessagesForCompany(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(messagesPage.getNumberOfElements()).isEqualTo(1);
        List<SmsMessageData> smsMessageDatas = messagesPage.getContent();

        SmsMessageData smsMessageData = smsMessageDatas.get(0);
        assertThat(smsMessageData.getFromCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(smsMessageData.getPhone()).isEqualTo(DEFAULT_CLIENT_PHONE);
        assertThat(smsMessageData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(smsMessageData.getSmsType()).isEqualTo(SmsType.DIRECT_MESSAGE);
    }

    private void doManualReplenishment(BigDecimal value) {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        TransactionData transactionData = new TransactionData();
        transactionData.setType(TransactionType.REPLENISHMENT);
        transactionData.setAccountId(accountData.getId());
        transactionData.setValue(value);
        transactionData.setCurrency(accountData.getCurrency());
        ManualReplenishmentDetailsData details = new ManualReplenishmentDetailsData();
        transactionData.setDetails(details);
        billingFacade.doReplenishment(transactionData);
    }

    @Test
    public void test_smsNotSendAndThrowedException_whenDirectSmsAndNotEnoughMoneyOnAccount() {
        AbonRuntimeException are = null;
        try {
            companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, "bla-bla", DEFAULT_COMPANY_ID);
        } catch (AbonRuntimeException e) {
            are = e;
        }
        assertThat(are).isNotNull();
        assertThat(are).isOfAnyClassIn(NotAllowedException.class);
        assertThat(are.getApiErrorCode()).isEqualTo(ApiErrorCode.NOT_ENOUGH_MONEY_ON_ACCOUNT);

        Page<SmsMessageData> messagesPage = companySmsFacade
                .findSmsMessagesForCompany(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(messagesPage.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    public void test_paymentDidNot_whenDirectSmsAndNotEnoughMoney() {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        AbonRuntimeException are = null;
        try {
            companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, "bla-bla", DEFAULT_COMPANY_ID);
        } catch (AbonRuntimeException e) {
            are = e;
        }
        AccountData accountDataAfterSendingSms = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        assertThat(accountData.getBalance()).isEqualByComparingTo(accountDataAfterSendingSms.getBalance());
    }

    @Test
    public void test_paymentDidAndTransactionExists_whenDirectSmsAndAccountHasEnoughMoney() {
        DateTime inStartOfTest = DateTime.now();
        doManualReplenishment(BigDecimal.TEN);
        CompanyData companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);
        PriceData smsPrice = scConfigurationService.getSmsPrice(companyData.getCountry());

        companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, "bla-bla", DEFAULT_COMPANY_ID);

        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        List<TransactionData> content = transactions.getContent().stream().filter(t -> t.getType().equals(TransactionType.PAYMENT)).collect(Collectors.toList());
        assertThat(content).hasSize(1);
        TransactionData transactionData = content.get(0);
        assertThat(transactionData.getValue()).isEqualByComparingTo(smsPrice.getValue());
        assertThat(transactionData.getCreatedDate()).isNotNull().isBetween(inStartOfTest, DateTime.now());
        assertThat(transactionData.getLastModifiedBy()).isEqualTo(JunitAuditor.JUNIT_AUDITOR);
    }

    @Test
    public void test_paymentDidAndAccountBalanceChanged_whenDirectSmsAndAccountHasEnoughMoney() {
        doManualReplenishment(BigDecimal.TEN);
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        CompanyData companyData = companyFacade.getCompany(DEFAULT_COMPANY_ID);
        PriceData smsPrice = scConfigurationService.getSmsPrice(companyData.getCountry());

        companySmsFacade.sendSmsMessage(DEFAULT_CARD_UUID, "bla-bla", DEFAULT_COMPANY_ID);
        AccountData accountDataAfterSendingSms = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        assertThat(accountData.getBalance()).isEqualByComparingTo(accountDataAfterSendingSms.getBalance().add(smsPrice.getValue()));
    }


}
