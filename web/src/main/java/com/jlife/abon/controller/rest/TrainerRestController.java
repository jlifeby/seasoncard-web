package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.TrainerData;
import com.jlife.abon.facade.PublicTrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class TrainerRestController extends BaseController {

    @Autowired
    private PublicTrainerFacade publicTrainerFacade;

    @GetMapping("/companies/{companyId}/trainers")
    public List<TrainerData> getActivePublishedTrainersForCompany(@PathVariable("companyId") String companyId) {
        return publicTrainerFacade.getActivePublishedTrainersForCompany(companyId);
    }

    @GetMapping("/trainers/{trainerId}")
    public TrainerData getTrainer(@PathVariable("trainerId") String trainerId) {
        return publicTrainerFacade.getTrainer(trainerId);
    }

}
