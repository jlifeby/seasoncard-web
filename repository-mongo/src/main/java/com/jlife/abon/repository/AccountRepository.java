package com.jlife.abon.repository;

import com.jlife.abon.entity.Account;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface AccountRepository extends EntityRepository<Account> {

    List<Account> findAllByCompanyId(String companyId);

}
