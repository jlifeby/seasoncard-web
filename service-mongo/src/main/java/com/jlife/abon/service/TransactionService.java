package com.jlife.abon.service;

import com.jlife.abon.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TransactionService {

    Page<Transaction> findTransactions(String companyId, Pageable pageable);
}
