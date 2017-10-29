package com.jlife.abon.facade;

import com.jlife.abon.dto.TrainerData;

import java.util.List;

/**
 * Facade to work with trainers as company.
 *
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyTrainerFacade {

    /**
     * Gets trainer by his id.
     *
     * @param id
     * @return
     */
    TrainerData getTrainer(String id);

    /**
     * Get all trainers for company. Doesn't matter active/published.
     *
     * @param companyId
     * @return
     */
    List<TrainerData> getAllTrainers(String companyId);

    /**
     * Gets only active trainers for company.
     *
     * @param companyId
     * @return
     */
    List<TrainerData> getAllActiveTrainers(String companyId);

    /**
     * Create trainer for company.
     *
     * @param trainer
     * @param companyId
     * @return
     */
    TrainerData createTrainer(TrainerData trainer, String companyId);

    /**
     * Updates trainers for company based on his id and new properties.
     *
     * @param trainerId
     * @param trainer
     * @param companyId
     * @return
     */
    TrainerData updateTrainer(String trainerId, TrainerData trainer, String companyId);

    /**
     *
     * Archive trainer for company based on his id.
     *
     * @param trainerId
     * @param companyId
     * @return
     */
    TrainerData archiveTrainer(String trainerId, String companyId);

}
