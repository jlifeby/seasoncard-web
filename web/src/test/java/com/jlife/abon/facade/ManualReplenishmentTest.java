package com.jlife.abon.facade;

import com.jlife.abon.TransactionType;
import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.dto.billing.ManualReplenishmentDetailsData;
import com.jlife.abon.dto.billing.TransactionData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class ManualReplenishmentTest extends ServiceBaseTestCase {

    @Autowired
    private BillingFacade billingFacade;

    @Test
    public void test_replenishmentedWithTen_whenZeroAtStart() {
        AccountData account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);

        TransactionData transactionData = new TransactionData();
        transactionData.setCurrency(account.getCurrency());
        transactionData.setValue(BigDecimal.TEN);
        transactionData.setAccountId(account.getId());
        transactionData.setType(TransactionType.REPLENISHMENT);

        ManualReplenishmentDetailsData details = new ManualReplenishmentDetailsData();
        details.setComment("based on ...");

        transactionData.setDetails(details);

        billingFacade.doReplenishment(transactionData);

        account = billingFacade.getAccountByCompanyId(DEFAULT_COMPANY_ID);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.TEN);

        Page<TransactionData> transactionPage = billingFacade.findTransactions(DEFAULT_COMPANY_ID, new PageRequest(0, 10));

        List<TransactionData> transactionDataList = transactionPage.getContent();
        assertThat(transactionDataList).hasSize(1);

        TransactionData firstTransaction = transactionDataList.get(0);

        assertThat(firstTransaction.getType()).isEqualTo(TransactionType.REPLENISHMENT);
        assertThat(firstTransaction.getDetails()).isOfAnyClassIn(ManualReplenishmentDetailsData.class);
        assertThat(firstTransaction.getValue()).isEqualTo(BigDecimal.TEN);
        assertThat(firstTransaction.getCurrency()).isEqualTo(account.getCurrency());
    }
}
