package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.facade.CatalogFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CatalogFacadeImpl extends AbstractFacade implements CatalogFacade {

    @Override
    public List<CompanyData> getPublishedCompanies() {
        List<Company> allPublishedCompanies = companyService.getAllPublishedCompanies();
        return dataMapper.toCompanyDataList(allPublishedCompanies);
    }
}
