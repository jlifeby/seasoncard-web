package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.error.NotAllowedException;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class MarkingAttendedWithCustomDateTest extends ServiceBaseTestCase {

    @Autowired
    EnrollFacade enrollFacade;
    @Autowired
    MarkingAttendedFacade markingAttendedFacade;

    @Test
    public void test_createDateEqualsVisitDate_whenMarkAttendedWithVisitDateNull() {
        AbonnementData abon = enrollAbon();
        AttendanceData attendance = new AttendanceData();
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setAbonnementId(abon.getId());
        AbonnementData abonnementData = markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);

        DateTime now = DateTime.now();
        List<AttendanceData> attendances = abonnementData.getAttendances();
        assertThat(attendances).isNotNull().hasSize(1);

        AttendanceData attendanceData = attendances.get(0);

        assertThat(attendanceData).isNotNull();
        assertThat(attendanceData.getCreatedDate()).isEqualTo(attendanceData.getVisitDate());
        assertThat(attendanceData.getCreatedDate().getMillis() / 1000.)
                .isCloseTo(now.getMillis() / 1000., within(1d));
    }

    @Test
    public void test_visitDateEqualsAndCustomCreatedEqualsNow_whenMarkAttendedWithVisitDateNull() {
        AbonnementData abon = enrollAbon();
        DateTime customVisitDate = DateTime.now().minusDays(2);
        AttendanceData attendance = new AttendanceData();
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setAbonnementId(abon.getId());
        attendance.setVisitDate(customVisitDate);
        AbonnementData abonnementData = markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);

        DateTime now = DateTime.now();
        List<AttendanceData> attendances = abonnementData.getAttendances();
        assertThat(attendances).isNotNull().hasSize(1);

        AttendanceData attendanceData = attendances.get(0);

        assertThat(attendanceData).isNotNull();
        assertThat(customVisitDate).isEqualTo(attendanceData.getVisitDate());
        assertThat(attendanceData.getCreatedDate().getMillis() / 1000.)
                .isCloseTo(now.getMillis() / 1000., within(1d));
    }

    @Test(expected = NotAllowedException.class)
    public void test_throwNotAllowedException_whenCustomVisitDateIsOusideActualPeriodOfAbon() {
        AbonnementData abon = enrollAbon();
        DateTime customVisitDate = DateTime.now().minusMonths(3);
        AttendanceData attendance = new AttendanceData();
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setAbonnementId(abon.getId());
        attendance.setVisitDate(customVisitDate);
        AbonnementData abonnementData = markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);
    }


    private AbonnementData enrollAbon() {
        AbonnementData sourceAbon = new AbonnementData();
        sourceAbon.setProductId(DEFAULT_PRODUCT_ID_BY_TIME);
        sourceAbon.setStartDate(new DateTime().minusMonths(1));
        sourceAbon.setEndDate(new DateTime().plusMonths(1));
        sourceAbon.setInitialCountOfUnits(0);
        sourceAbon.setInitialCountOfUnits(60);
        sourceAbon.setProductPrice(55d);
        sourceAbon.addComment("bla-bla-junit");
        return enrollFacade.enrollAbonnement(DEFAULT_CARD_UUID, DEFAULT_COMPANY_ID, sourceAbon);
    }
}
