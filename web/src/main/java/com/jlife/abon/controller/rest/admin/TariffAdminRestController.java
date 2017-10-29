package com.jlife.abon.controller.rest.admin;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.facade.TariffFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
public class TariffAdminRestController extends BaseController {

    @Autowired
    private TariffFacade tariffFacade;

    @GetMapping("/api/admin/tariffs/calculate")
    TariffData calculateTariffData(@RequestParam String companyId,
                                   @RequestParam int countOfMonth,
                                   @RequestParam String tariffId) {
        return tariffFacade.calculateTariff(countOfMonth, tariffId, companyId);
    }

}
