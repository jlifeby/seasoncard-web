package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.enumeration.Country;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class UpdatingCompanyTest  extends ServiceBaseTestCase {

    @Test
    public void test_countryNotUpdated_whenChangedCountry(){
        CompanyData company = companyFacade.getCompany(DEFAULT_COMPANY_ID);

        assertThat(company).isNotNull();
        assertThat(company.getCountry()).isEqualTo(Country.RUSSIA);

        company.setCountry(Country.BELARUS);
        CompanyData updateCompany = companyFacade.updateCompanyOwn(DEFAULT_COMPANY_ID, company);

        assertThat(updateCompany.getCountry()).isEqualTo(Country.RUSSIA);
    }
}
