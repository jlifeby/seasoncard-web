package com.jlife.abon.facade;

import com.jlife.abon.dto.billing.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class TransactionTest extends ServiceBaseTestCase {

    @Autowired
    BillingFacade billingFacade;

    @Test
    public void test_emptyList_whenThereAreNotTransaction() {
        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(transactions).isNotNull();
        assertThat(transactions.getContent()).isNotNull().isEmpty();
    }

    @Test
    public void test_hasOneTransaction_whenAddedRaw() {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        TransactionData transactionData = new TransactionData();
        transactionData.setAccountId(accountData.getId());
        transactionData.setCurrency(accountData.getCurrency());
        transactionData.setValue(BigDecimal.ZERO);
        billingFacade.saveRaw(transactionData);

        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(transactions).isNotNull();
        assertThat(transactions.getContent()).isNotNull().hasSize(1);
    }

    @Test
    public void test_hasManualReplenishmentTransaction_whenAddedRaw() {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        TransactionData transactionData = new TransactionData();
        transactionData.setAccountId(accountData.getId());
        transactionData.setCurrency(accountData.getCurrency());
        transactionData.setValue(BigDecimal.ZERO);
        ManualReplenishmentDetailsData detailsData = new ManualReplenishmentDetailsData();
        detailsData.setComment("based on ...");
        transactionData.setDetails(detailsData);
        billingFacade.saveRaw(transactionData);

        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(transactions).isNotNull();
        assertThat(transactions.getContent()).isNotNull().hasSize(1);
        TransactionData storedTransaction = transactions.getContent().get(0);
        assertThat(storedTransaction.getDetails()).isNotNull().isOfAnyClassIn(ManualReplenishmentDetailsData.class);
    }

    @Test
    public void test_hasSmsPaymentTransaction_whenAddedRaw() {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        TransactionData transactionData = new TransactionData();
        transactionData.setAccountId(accountData.getId());
        transactionData.setCurrency(accountData.getCurrency());
        transactionData.setValue(BigDecimal.ZERO);
        TransactionDetailsData detailsData = new SmsPaymentDetailsData();
        detailsData.setComment("based on sms ...");
        transactionData.setDetails(detailsData);
        billingFacade.saveRaw(transactionData);

        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(transactions).isNotNull();
        assertThat(transactions.getContent()).isNotNull().hasSize(1);
        TransactionData storedTransaction = transactions.getContent().get(0);
        assertThat(storedTransaction.getDetails()).isNotNull().isOfAnyClassIn(SmsPaymentDetailsData.class);
    }

    @Test
    public void test_hasSmsGroupPaymentTransaction_whenAddedRaw() {
        AccountData accountData = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);

        TransactionData transactionData = new TransactionData();
        transactionData.setAccountId(accountData.getId());
        transactionData.setCurrency(accountData.getCurrency());
        transactionData.setValue(BigDecimal.ZERO);
        TransactionDetailsData detailsData = new SmsGroupPaymentDetailsData();
        detailsData.setComment("based on sms group ...");
        transactionData.setDetails(detailsData);
        billingFacade.saveRaw(transactionData);

        Page<TransactionData> transactions = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));
        assertThat(transactions).isNotNull();
        assertThat(transactions.getContent()).isNotNull().hasSize(1);
        TransactionData storedTransaction = transactions.getContent().get(0);
        assertThat(storedTransaction.getDetails()).isNotNull().isOfAnyClassIn(SmsGroupPaymentDetailsData.class);
    }

}
