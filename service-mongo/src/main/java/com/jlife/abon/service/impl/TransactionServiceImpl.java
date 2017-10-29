package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Account;
import com.jlife.abon.entity.Transaction;
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
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BillingService billingService;

    @Override
    public Page<Transaction> findTransactions(String companyId, Pageable pageable) {
        Account account = billingService.getAccountOrCreate(companyId);
        return transactionRepository.findAllByAccountId(account.getId(), pageable);
    }
}
