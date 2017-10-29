package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;

/**
 * Facade to provide enrolling functionality in easy way.
 * <p>
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface EnrollFacade {

    /**
     * Enrolls abonnement to user with card as company
     *
     * @param cardUUID
     * @param companyId
     * @param abonnement
     * @return
     */
    AbonnementData enrollAbonnement(Long cardUUID, String companyId, AbonnementData abonnement);
}
