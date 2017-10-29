package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanySelfRegistrationFacade {
    CompanyData selfRegisterCompany(CompanyData company, UserData admin);
}
