package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.facade.CompanyStatisticFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api")
public class CompanyStatisticRestController extends BaseController {

    @Autowired
    private CompanyStatisticFacade companyStatisticFacade;

    @GetMapping("/company/current-statistic")
    public CurrentStatisticData getCurrentStatistic() {
        return companyStatisticFacade.getCurrentStatistic(getSessionCompanyId());
    }

}
