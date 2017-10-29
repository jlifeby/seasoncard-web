package com.jlife.abon.repository;

import com.jlife.abon.entity.Company;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface CompanyRepository extends EntityRepository<Company> {

    List<Company> findByPublished(boolean published);

    Company findOneByContractId(int contractId);
}
