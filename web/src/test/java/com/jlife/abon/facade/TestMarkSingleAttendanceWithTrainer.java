package com.jlife.abon.facade;

import com.jlife.abon.dto.SingleAttendanceData;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class TestMarkSingleAttendanceWithTrainer extends ServiceBaseTestCase {

    @Autowired
    SingleAttendanceFacade singleAttendanceFacade;

    @Autowired
    EnrollFacade enrollFacade;

    @Test
    public void deprecatedMarkSingleAttendanceWithoutTrainer() {
        SingleAttendanceData singleAttendance = singleAttendanceFacade.markSingleAttendance(DEFAULT_COMPANY_ID, 4, "big man");

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertNull(stored.getTrainerId());
    }

    @Test
    public void deprecatedMarkSingleAttendanceWithTrainer() {
        SingleAttendanceData singleAttendance = singleAttendanceFacade
                .markSingleAttendance(DEFAULT_COMPANY_ID, 4, DEFAULT_TRAINER_ID, "big man");

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertEquals(DEFAULT_TRAINER_ID, stored.getTrainerId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testMarkSingleAttendanceWithNotExistedTrainer() {
        SingleAttendanceData singleAttendance = singleAttendanceFacade
                .markSingleAttendance(DEFAULT_COMPANY_ID, 4, "other trainer", "big man");

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertEquals(DEFAULT_TRAINER_ID, stored.getTrainerId());
    }

    @Test(expected = NotAllowedException.class)
    public void testMarkSingleAttendanceWithNotNotCompanyTrainer() {
        SingleAttendanceData singleAttendance = singleAttendanceFacade
                .markSingleAttendance(DEFAULT_COMPANY_ID, 4, NOT_COMPANY_TRAINER_ID, "big man");

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertEquals(DEFAULT_TRAINER_ID, stored.getTrainerId());
    }


    @Test
    public void testMarkSingleAttendanceWithTrainer() {
        SingleAttendanceData sourceSingleAttendance = new SingleAttendanceData();
        sourceSingleAttendance.setPrice(2);
        sourceSingleAttendance.setTrainerId(DEFAULT_TRAINER_ID);
        SingleAttendanceData singleAttendance = singleAttendanceFacade
                .markSingleAttendance(DEFAULT_COMPANY_ID, sourceSingleAttendance);

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertEquals(DEFAULT_TRAINER_ID, stored.getTrainerId());
    }

    @Test
    public void testMarkSingleAttendanceWithoutTrainer() {
        SingleAttendanceData sourceSingleAttendance = new SingleAttendanceData();
        sourceSingleAttendance.setPrice(3);
        SingleAttendanceData singleAttendance = singleAttendanceFacade
                .markSingleAttendance(DEFAULT_COMPANY_ID, sourceSingleAttendance);

        SingleAttendanceData stored = singleAttendanceFacade.getOne(singleAttendance.getId());
        assertNull(stored.getTrainerId());
    }

}
