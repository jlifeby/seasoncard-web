package com.jlife.abon.repository;

import com.jlife.abon.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author Dzmitry Misiuk
 */
public interface TransactionRepository extends EntityRepository<Transaction> {

    Page<Transaction> findAllByAccountId(String accountId, Pageable pageable);

}
