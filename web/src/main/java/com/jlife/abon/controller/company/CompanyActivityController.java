package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.AbstractActivityController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyActivityController extends AbstractActivityController {

    @RequestMapping(path = "/activity")
    public String getCompanyActivities(ModelMap model) {
        String companyId = getSessionCompanyId();
        return getCompanyActivityPage(model, companyId);
    }

}
