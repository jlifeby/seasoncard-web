package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.TrainerData;
import com.jlife.abon.facade.CompanyTrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api")
public class CompanyTrainerRestController extends BaseController {

    @Autowired
    private CompanyTrainerFacade companyTrainerFacade;

    @GetMapping("/company/trainers")
    public List<TrainerData> getAllTrainers() {
        return companyTrainerFacade.getAllTrainers(getSessionCompanyId());
    }

    @GetMapping("/company/trainers/active")
    public List<TrainerData> getActiveTrainers() {
        return companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId());
    }
}
