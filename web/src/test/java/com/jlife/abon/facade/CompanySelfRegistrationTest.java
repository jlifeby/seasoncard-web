package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.dto.UserData;
import org.assertj.core.data.Offset;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class CompanySelfRegistrationTest extends ServiceBaseTestCase {

    @Autowired
    CompanySelfRegistrationFacade selfRegistrationFacade;

    @Value("${tariff.free.duration.days}")
    private int durationOfFreeTariffDays;

    @Test
    public void test_setFreeTariff_whenSelfRegister() {
        UserData admin = prepareAdmin();
        CompanyData company = prepareCompany();
        CompanyData registeredCompany = selfRegistrationFacade.selfRegisterCompany(company, admin);

        assertThat(registeredCompany).isNotNull();
        assertThat(registeredCompany.getId()).isNotNull();

        TariffData currentTariff = registeredCompany.getCurrentTariff();
        assertThat(currentTariff).isNotNull();

        assertThat(currentTariff.isFree()).isEqualTo(true);
        assertThat(currentTariff.getId()).isEqualTo(TARIFF_FREE_ID);
        assertThat(currentTariff.getMonthPrice()).isEqualTo(0, Offset.offset(0.01));

        assertThat(currentTariff.getTotalPrice()).isEqualTo(0, Offset.offset(0.01));
    }

    @Test
    public void test_setDurationOfFreeTariff_WhenSelfRegister() {
        DateTime startingTestTime = DateTime.now();
        UserData admin = prepareAdmin();
        CompanyData company = prepareCompany();
        CompanyData registeredCompany = selfRegistrationFacade.selfRegisterCompany(company, admin);

        TariffData currentTariff = registeredCompany.getCurrentTariff();

        assertThat(currentTariff.getStartDate()).isBetween(startingTestTime,
                DateTime.now());

        assertThat(currentTariff.getEndDate()).isBetween(startingTestTime.plusDays(durationOfFreeTariffDays),
                DateTime.now().plusDays(durationOfFreeTariffDays));
    }

    private UserData prepareAdmin() {
        UserData userData = new UserData();
        userData.setEmail("junit@junit.com");
        userData.setName("junit");
        userData.setLastName("junit");
        return userData;
    }


    private CompanyData prepareCompany() {
        CompanyData companyData = new CompanyData();
        companyData.setName("junit-company");
        return companyData;
    }

}
