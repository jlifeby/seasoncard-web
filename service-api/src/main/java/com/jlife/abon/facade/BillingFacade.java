package com.jlife.abon.facade;

import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.dto.billing.TransactionData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface BillingFacade {

    TransactionData doPayment(TransactionData transactionData);

    TransactionData doReplenishment(TransactionData transactionData);

    Page<TransactionData> findTransactions(String companyId, Pageable pageable);

    TransactionData getTransactionAsCompany(String id, String companyId);

    AccountData getAccountByCompanyId(String companyId);

    AccountData saveRaw(AccountData accountData);

    TransactionData saveRaw(TransactionData transactionData);
}
