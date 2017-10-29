package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */

public class EnrollingAbonnementWithTrainerTest extends ServiceBaseTestCase {

    @Autowired
    EnrollFacade enrollFacade;

    @Test
    public void deprecatedEnrollAbonWithTrainerId() {
        AbonnementData abonnement = cardFacade.enrollProduct(
                DEFAULT_CARD_UUID,
                DEFAULT_PRODUCT_ID_BY_TIME,
                DEFAULT_COMPANY_ID,
                new DateTime(), new DateTime().plusMonths(1),
                10,
                60,
                55d,
                DEFAULT_TRAINER_ID,
                "bla-bla-junit");
        assertNotNull(abonnement);
        assertNotNull(abonnement.getId());
        AbonnementData storedAbon = abonnementFacade.getAbonnementById(abonnement.getId(), DEFAULT_COMPANY_ID);
        assertEquals(DEFAULT_TRAINER_ID, abonnement.getTrainerId());
    }

    @Test
    public void enrollAbonWithTrainerId() {
        AbonnementData sourceAbon = new AbonnementData();
        sourceAbon.setProductId(DEFAULT_PRODUCT_ID_BY_TIME);
        sourceAbon.setStartDate(new DateTime());
        sourceAbon.setEndDate(new DateTime().plusMonths(1));
        sourceAbon.setInitialCountOfUnits(0);
        sourceAbon.setInitialCountOfUnits(60);
        sourceAbon.setProductPrice(55d);
        sourceAbon.addComment("bla-bla-junit");
        sourceAbon.setTrainerId(DEFAULT_TRAINER_ID);
        AbonnementData abonnement = enrollFacade.enrollAbonnement(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID, sourceAbon);

        assertNotNull(abonnement);
        assertNotNull(abonnement.getId());
        AbonnementData storedAbon = abonnementFacade.getAbonnementById(abonnement.getId(), DEFAULT_COMPANY_ID);
        assertEquals(DEFAULT_TRAINER_ID, abonnement.getTrainerId());
    }

    @Test
    public void deprecatedEnrollAbonWithoutTrainerId() {
        AbonnementData abonnement = cardFacade.enrollProduct(
                DEFAULT_CARD_UUID,
                DEFAULT_PRODUCT_ID_BY_TIME,
                DEFAULT_COMPANY_ID,
                new DateTime(), new DateTime().plusMonths(1),
                10,
                60,
                55d,
                "bla-bla-junit");
        assertNotNull(abonnement);
        assertNotNull(abonnement.getId());
        AbonnementData storedAbon = abonnementFacade.getAbonnementById(abonnement.getId(), DEFAULT_COMPANY_ID);
        assertEquals(DEFAULT_PRODUCT_ID_BY_TIME, abonnement.getProductId());
        assertEquals(DEFAULT_COMPANY_ID, abonnement.getProduct().getCompanyId());
        assertEquals(55d, abonnement.getProduct().getPrice(), 0.01);
        assertNull(abonnement.getTrainerId());
    }

    @Test
    public void enrollAbonWithoutTrainerId() {
        AbonnementData sourceAbon = new AbonnementData();
        sourceAbon.setProductId(DEFAULT_PRODUCT_ID_BY_TIME);
        sourceAbon.setStartDate(new DateTime());
        sourceAbon.setEndDate(new DateTime().plusMonths(1));
        sourceAbon.setInitialCountOfUnits(0);
        sourceAbon.setInitialCountOfUnits(60);
        sourceAbon.setProductPrice(55d);
        sourceAbon.addComment("bla-bla-junit");
        AbonnementData abonnement = enrollFacade.enrollAbonnement(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID, sourceAbon);

        assertNotNull(abonnement);
        assertNotNull(abonnement.getId());
        AbonnementData storedAbon = abonnementFacade.getAbonnementById(abonnement.getId(), DEFAULT_COMPANY_ID);
        assertEquals(DEFAULT_PRODUCT_ID_BY_TIME, abonnement.getProductId());
        assertEquals(DEFAULT_COMPANY_ID, abonnement.getProduct().getCompanyId());
        assertEquals(55d, abonnement.getProduct().getPrice(), 0.01);
        assertNull(abonnement.getTrainerId());
    }
}
