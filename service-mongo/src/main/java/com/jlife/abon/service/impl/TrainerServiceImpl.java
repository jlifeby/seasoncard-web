package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Trainer;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.EntityRepository;
import com.jlife.abon.repository.TrainerRepository;
import com.jlife.abon.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public Trainer getTrainer(String id) {
        Trainer trainer = trainerRepository.findOne(id);
        if (trainer == null) {
            throw new ResourceNotFoundException(ApiErrorCode.TRAINER_NOT_FOUND_FOR_ID, id);
        }
        return trainer;
    }

    @Override
    public List<Trainer> getActivePublishedTrainersForCompany(String companyId) {
        return trainerRepository.findByActiveAndPublishedAndCompanyId(true, true, companyId);
    }

    @Override
    public List<Trainer> getOwnTrainersAsCompany(String companyId) {
        return trainerRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Trainer> getOwnActiveTrainersAsCompany(String companyId) {
        return trainerRepository.findByActiveAndCompanyId(true, companyId);
    }

    @Override
    public Trainer createTrainerAsCompany(Trainer trainer, String companyId) {
        trainer.setCompanyId(companyId);
        trainer.setActive(true);
        trainer.setId(null);
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainerAsCompany(String trainerId, Trainer trainer, String companyId) {
        Trainer existingTrainer = getTrainer(trainerId);
        if (!existingTrainer.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_TRAINER,
                    companyId, trainerId);
        }
        existingTrainer.setName(trainer.getName());
        existingTrainer.setLastName(trainer.getLastName());
        existingTrainer.setMiddleName(trainer.getMiddleName());
        existingTrainer.setPublished(trainer.isPublished());
        existingTrainer.setDescription(trainer.getDescription());
        existingTrainer.setHtmlContent(trainer.getHtmlContent());
        existingTrainer.setLogoPath(trainer.getLogoPath());
        existingTrainer.setBirthday(trainer.getBirthday());
        existingTrainer.setPhone(trainer.getPhone());
        return trainerRepository.save(existingTrainer);
    }

    @Override
    public Trainer archiveTrainerAsCompany(String trainerId, String companyId) {
        Trainer existingTrainer = getTrainer(trainerId);
        if (!existingTrainer.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_TRAINER,
                    companyId, trainerId);
        }
        existingTrainer.setActive(false);
        return trainerRepository.save(existingTrainer);
    }

    @Override
    public EntityRepository<Trainer> getBaseRepository() {
        return trainerRepository;
    }

    @Override
    public String getBaseEntityName() {
        return Trainer.class.getSimpleName();
    }
}
