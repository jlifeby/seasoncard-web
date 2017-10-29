package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.facade.CompanySelfRegistrationFacade;
import com.jlife.abon.service.SelfRegistrationService;
import com.jlife.abon.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanySelfRegistrationFacadeImpl extends AbstractFacade implements CompanySelfRegistrationFacade {

    @Value("${app.free_virtual_card_amount}")
    public int freeVirtualCardAmount;

    @Autowired
    private SelfRegistrationService selfRegistrationService;
    @Autowired
    private TariffService tariffService;

    @Override
    public CompanyData selfRegisterCompany(CompanyData companyData, UserData userData) {
        Assert.notNull(userData.getEmail(), "user email is required");
        Assert.notNull(userData.getName(), "user name is required");
        Assert.notNull(userData.getLastName(), "user last name is required");
        Assert.notNull(companyData.getName(), "company name is required");
        Assert.notNull(companyData.getCountry(), "company country is required");
        if (!userService.isFreeEmailForUser(userData.getEmail())) {
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, userData.getEmail());
        }
        if (!selfRegistrationService.isFreeCardsAvailableToRegister(freeVirtualCardAmount)) {
            throw new NotAllowedException(ApiErrorCode.NOT_ENOUGH_VIRTUAL_CARDS_TO_REGISTER_NEW_COMPANY, userData.getEmail());
        }
        Company createdCompany = selfRegistrationService.selfRegisterCompany(dataMapper.fromCompanyData(companyData));
        User createdAdmin = selfRegistrationService.registerAdminForCompany(dataMapper.fromUserData(userData), createdCompany);
        String companyId = createdCompany.getId();
        createdCompany = tariffService.setFreeTariffForCompany(companyId);
        adminService.sendCompanyAdminCredentials(companyId, createdAdmin.getId());
        adminService.addVirtualCardsToCompany(companyId, freeVirtualCardAmount);
        return dataMapper.toCompanyData(createdCompany);
    }

}
