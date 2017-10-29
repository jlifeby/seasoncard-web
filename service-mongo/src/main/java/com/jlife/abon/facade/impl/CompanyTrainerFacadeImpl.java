package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.TrainerData;
import com.jlife.abon.entity.Trainer;
import com.jlife.abon.facade.CompanyTrainerFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyTrainerFacadeImpl extends AbstractFacade implements CompanyTrainerFacade {

    @Override
    public TrainerData getTrainer(String id) {
        Trainer trainer = trainerService.getTrainer(id);
        return dataMapper.toTrainerData(trainer);
    }

    @Override
    public List<TrainerData> getAllTrainers(String companyId) {
        List<Trainer> trainers = trainerService.getOwnTrainersAsCompany(companyId);
        return dataMapper.toTrainerDataList(trainers);
    }

    @Override
    public List<TrainerData> getAllActiveTrainers(String companyId) {
        List<Trainer> trainerList = trainerService.getOwnActiveTrainersAsCompany(companyId);
        return dataMapper.toTrainerDataList(trainerList);
    }

    @Override
    public TrainerData createTrainer(TrainerData trainer, String companyId) {
        Trainer savedTrainer = trainerService.createTrainerAsCompany(dataMapper.fromTrainerData(trainer), companyId);
        return dataMapper.toTrainerData(savedTrainer);
    }

    @Override
    public TrainerData updateTrainer(String trainerId, TrainerData trainerData, String companyId) {
        Trainer updatedTrainer = trainerService.updateTrainerAsCompany(trainerId,
                dataMapper.fromTrainerData(trainerData), companyId);
        return dataMapper.toTrainerData(updatedTrainer);
    }

    @Override
    public TrainerData archiveTrainer(String trainerId, String companyId) {
        Trainer archivedTrainer = trainerService.archiveTrainerAsCompany(trainerId, companyId);
        return dataMapper.toTrainerData(archivedTrainer);
    }
}
