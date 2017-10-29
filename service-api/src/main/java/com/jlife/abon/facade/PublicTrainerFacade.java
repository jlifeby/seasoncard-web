package com.jlife.abon.facade;

import com.jlife.abon.dto.TrainerData;

import java.util.List;

/**
 * Facade to get trainers as any user.
 *
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface PublicTrainerFacade {

    /**
     * Gets active published trainers for company.
     *
     * @param companyId
     * @return
     */
    List<TrainerData> getActivePublishedTrainersForCompany(String companyId);

    /**
     * Gets trainer by id.
     *
     * @param id
     * @return
     */
    TrainerData getTrainer(String id);

    TrainerData save(TrainerData rowTrainer);
}
