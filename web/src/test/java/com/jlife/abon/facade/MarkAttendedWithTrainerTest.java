package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class MarkAttendedWithTrainerTest extends ServiceBaseTestCase {

    @Autowired
    MarkingAttendedFacade markingAttendedFacade;

    @Autowired
    EnrollFacade enrollFacade;

    @Test
    public void markAttendedWithoutTrainer() {
        AbonnementData abonnement = enrollAbonnement();
        assertEquals(0, abonnement.getAttendances().size());

        AttendanceData attendance = new AttendanceData();
        attendance.setAbonnementId(abonnement.getId());
        abonnement = markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);

        assertEquals(1, abonnement.getCountOfAttendances());
        assertNull(abonnement.getAttendances().get(0).getTrainerId());

    }

    @Test
    public void deprecatedMarkAttendedWithoutTrainer() {
        AbonnementData abonnement = enrollAbonnement();
        assertEquals(0, abonnement.getAttendances().size());

        AttendanceData attendance = new AttendanceData();
        attendance.setAbonnementId(abonnement.getId());
        abonnement = abonnementFacade.markAttended(abonnement.getId(), DEFAULT_COMPANY_ID, 0);

        assertEquals(1, abonnement.getCountOfAttendances());
        assertNull(abonnement.getAttendances().get(0).getTrainerId());

    }


    @Test
    public void markAttendedWithTrainer() {
        AbonnementData abonnement = enrollAbonnement();
        assertEquals(0, abonnement.getAttendances().size());

        AttendanceData attendance = new AttendanceData();
        attendance.setAbonnementId(abonnement.getId());
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        abonnement = markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);

        assertEquals(1, abonnement.getCountOfAttendances());
        assertEquals(DEFAULT_TRAINER_ID, abonnement.getAttendances().get(0).getTrainerId());
    }


    @Test
    public void deprecatedMarkAttendedWithTrainer() {
        AbonnementData abonnement = enrollAbonnement();
        assertEquals(0, abonnement.getAttendances().size());

        AttendanceData attendance = new AttendanceData();
        attendance.setAbonnementId(abonnement.getId());
        abonnement = abonnementFacade.markAttended(abonnement.getId(), DEFAULT_COMPANY_ID, 0, DEFAULT_TRAINER_ID);

        assertEquals(1, abonnement.getCountOfAttendances());
        assertEquals(DEFAULT_TRAINER_ID, abonnement.getAttendances().get(0).getTrainerId());

    }

    private AbonnementData enrollAbonnement() {
        AbonnementData abonnement = new AbonnementData();
        abonnement.setProductId(DEFAULT_PRODUCT_ID_BY_TIME);
        return enrollFacade.enrollAbonnement(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID, abonnement);
    }


}
