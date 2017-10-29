package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.ConsolidatedAbonnementData;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface AbonnementFacade {
    List<AbonnementData> getActiveAbonnementsForUser(String userId);

    @Deprecated
    AbonnementData markAttended(String abonnementId, String companyId, int markUnits);

    @Deprecated
    AbonnementData markAttended(String abonnementId, String companyId, int markUnits, String trainerId);

    AbonnementData getAbonnementByCompany(String abonnementId, String companyId);

    AbonnementData getAbonnementByUserCardId(String abonnementId, String cardId);

    AbonnementData getAbonnementByUserCard(String abonnementId, String cardId);

    ConsolidatedAbonnementData getConsolidatedAbonnement(String userId, String productId);

    List<AbonnementData> getLastAbonnementsForUser(String userId);

    AbonnementData changeEndDate(String abonnementId, DateTime newEndDate, String comment, String companyId);

    AbonnementData getAbonnementById(String abonnementId, String companyId);

    AbonnementData addComment(String abonnementId, String comment, String companyId);

    ConsolidatedAbonnementData getConsolidatedAbonnementAsCompany(String userId, String productId, String companyId);

    List<AbonnementData> findAll();

    AbonnementData findOne(String abonnementId);

    AbonnementData save(AbonnementData abonnement);
}
