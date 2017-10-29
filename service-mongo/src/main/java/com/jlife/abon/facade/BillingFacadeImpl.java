package com.jlife.abon.facade;

import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.dto.billing.TransactionData;
import com.jlife.abon.entity.Account;
import com.jlife.abon.entity.Transaction;
import com.jlife.abon.facade.impl.AbstractFacade;
import com.jlife.abon.repository.AccountRepository;
import com.jlife.abon.repository.TransactionRepository;
import com.jlife.abon.service.BillingService;
import com.jlife.abon.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class BillingFacadeImpl extends AbstractFacade implements BillingFacade {

    @Autowired
    private BillingService billingService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public TransactionData doPayment(TransactionData transactionData) {
        return null;
    }

    @Override
    public TransactionData doReplenishment(TransactionData transactionData) {
        Transaction transaction = billingService.doReplenishment(dataMapper.toTransaction(transactionData));
        return dataMapper.toTransactionData(transaction);
    }

    @Override
    public Page<TransactionData> findTransactions(String companyId, Pageable pageable) {
        Page<Transaction> transactions = transactionService.findTransactions(companyId, pageable);
        return transactions.map(t -> dataMapper.toTransactionData(t));
    }

    @Override
    public TransactionData getTransactionAsCompany(String id, String companyId) {
        return null;
    }

    @Override
    public AccountData getAccountByCompanyId(String companyId) {
        Account account = billingService.getAccountOrCreate(companyId);
        return dataMapper.toAccountData(account);
    }

    @Override
    public AccountData saveRaw(AccountData accountData) {
        Account account = accountRepository.save(dataMapper.toAccount(accountData));
        return dataMapper.toAccountData(account);
    }

    @Override
    public TransactionData saveRaw(TransactionData transactionData) {
        Transaction transaction = dataMapper.toTransaction(transactionData);
        return dataMapper.toTransactionData(transactionRepository.save(transaction));
    }
}
