package com.jlife.abon.repository;

import com.jlife.abon.entity.Trainer;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TrainerRepository extends EntityRepository<Trainer> {
    List<Trainer> findByActiveAndPublishedAndCompanyId(boolean active, boolean published, String companyId);

    List<Trainer> findByCompanyId(String companyId);

    List<Trainer> findByActiveAndCompanyId(boolean active, String companyId);
}
