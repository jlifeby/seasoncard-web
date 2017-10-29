package com.jlife.abon.repository;

import com.jlife.abon.entity.Office;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface OfficeRepository extends EntityRepository<Office> {

    List<Office> findByCompanyId(String companyId);
}
