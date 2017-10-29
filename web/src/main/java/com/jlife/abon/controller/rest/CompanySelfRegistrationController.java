package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.facade.CompanySelfRegistrationFacade;
import com.jlife.abon.form.CompanySelfRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
public class CompanySelfRegistrationController extends BaseController {

    @Autowired
    private CompanySelfRegistrationFacade selfRegistrationFacade;

    @PostMapping(value = "/api/register-company-self", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompanyData registerCompany(@RequestBody CompanySelfRegistrationForm registrationForm) {
        throw new NotAllowedException(ApiErrorCode.GENERAL_CODE, "Registration of new company is disabled");
//        // todo add validation on REST level
//        CompanyData companyData = registrationForm.toCompany();
//        UserData userData = registrationForm.extractUser();
//        CompanyData company = selfRegistrationFacade.selfRegisterCompany(companyData, userData);
//        return company;
    }

}
