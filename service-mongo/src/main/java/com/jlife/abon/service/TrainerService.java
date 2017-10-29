package com.jlife.abon.service;

import com.jlife.abon.entity.Trainer;

import java.util.List;

/**
 * Service to work with trainer. In general crud with company restriction.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TrainerService extends EntityAbstractService<Trainer> {

    /**
     * Gets trainer by his id.
     *
     * @param id
     * @return
     */
    Trainer getTrainer(String id);

    /**
     * Gets active published trainers for company.
     *
     * @param companyId
     * @return
     */
    List<Trainer> getActivePublishedTrainersForCompany(String companyId);

    /**
     * Gets all company's trainers by its admin.
     *
     * @param companyId
     * @return
     */
    List<Trainer> getOwnTrainersAsCompany(String companyId);

    /**
     * Gets all company's trainers by its admin.
     *
     * @param companyId
     * @return
     */
    List<Trainer> getOwnActiveTrainersAsCompany(String companyId);

    /**
     * Creates company trainer.
     *
     * @param trainer
     * @param companyId
     * @return
     */
    Trainer createTrainerAsCompany(Trainer trainer, String companyId);

    /**
     * Update company trainer.
     *
     * @param trainerId
     * @param trainer
     * @param companyId
     * @return
     */
    Trainer updateTrainerAsCompany(String trainerId, Trainer trainer, String companyId);

    /**
     * Archive company trainer.
     *
     * @param trainerId
     * @param companyId
     * @return
     */
    Trainer archiveTrainerAsCompany(String trainerId, String companyId);
}
