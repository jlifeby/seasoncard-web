package com.jlife.abon.controller.admin;

import com.jlife.abon.controller.application.AbstractActivityController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminCompanyActivityController extends AbstractActivityController {

    @RequestMapping(path = "/companies/{companyId}/activity")
    public String getCompanyActivities(ModelMap model, @PathVariable String companyId) {
        return getCompanyActivityPage(model, companyId);
    }
}
