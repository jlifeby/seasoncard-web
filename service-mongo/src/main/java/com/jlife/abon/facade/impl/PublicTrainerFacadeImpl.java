package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.TrainerData;
import com.jlife.abon.entity.Trainer;
import com.jlife.abon.facade.PublicTrainerFacade;
import com.jlife.abon.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class PublicTrainerFacadeImpl extends AbstractFacade implements PublicTrainerFacade {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public List<TrainerData> getActivePublishedTrainersForCompany(String companyId) {
        List<Trainer> activePublishedTrainersForCompany = trainerService.getActivePublishedTrainersForCompany(companyId);
        return dataMapper.toTrainerDataList(activePublishedTrainersForCompany);
    }

    @Override
    public TrainerData getTrainer(String id) {
        Trainer trainer = trainerService.getTrainer(id);
        return dataMapper.toTrainerData(trainer);
    }

    @Override
    public TrainerData save(TrainerData rowTrainer) {
        Trainer savedTrainer = trainerRepository.save(dataMapper.fromTrainerData(rowTrainer));
        return dataMapper.toTrainerData(savedTrainer);
    }
}
