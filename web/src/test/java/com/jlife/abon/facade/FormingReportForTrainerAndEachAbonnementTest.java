package com.jlife.abon.facade;


import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportEntry;
import com.jlife.abon.report.ReportKey;
import com.jlife.abon.report.ReportPeriod;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class FormingReportForTrainerAndEachAbonnementTest extends ServiceBaseTestCase {

    @Autowired
    EnrollFacade enrollFacade;

    @Autowired
    MarkingAttendedFacade markingAttendedFacade;

    @Autowired
    ReportFacade reportFacade;

    @Test
    public void test_oneAttendanceToday_whenHaveOnlyOneAttendanceToday() {
        AbonnementData abonnementData = enrollAbon();
        markOneToday(abonnementData);

        final DateTime today = DateTime.now();
        Report report = reportFacade.generateReportForTrainer(DEFAULT_COMPANY_ID,
                new ReportPeriod(today, today).normalize(),
                DEFAULT_TRAINER_ID, ReportTrainerType.BY_ATTENDANCES);

        assertThat(report).isNotNull();

        List<ReportEntry> entries = report.getEntries();
        assertThat(entries.size()).isEqualTo(1);
        LinkedHashMap<Object, Object> pairs = entries.get(0).getPairs();
        assertThat(pairs).isNotNull();
        assertThat(pairs.get(ReportKey.PRODUCT_NAME)).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(pairs.get(ReportKey.COUNT_ATTENDANCES)).isEqualTo(1);
    }

    @Test
    public void test_oneAttendanceToday_whenHaveOneTodayAndOneYesterday() {
        AbonnementData abonnementData = enrollAbon();
        markOneToday(abonnementData);
        markOneYesterday(abonnementData);
        final DateTime today = DateTime.now();
        Report report = reportFacade.generateReportForTrainer(DEFAULT_COMPANY_ID,
                new ReportPeriod(today, today).normalize(),
                DEFAULT_TRAINER_ID, ReportTrainerType.BY_ATTENDANCES);

        assertThat(report).isNotNull();

        List<ReportEntry> entries = report.getEntries();
        assertThat(entries.size()).isEqualTo(1);
        LinkedHashMap<Object, Object> pairs = entries.get(0).getPairs();
        assertThat(pairs).isNotNull();
        assertThat(pairs.get(ReportKey.PRODUCT_NAME)).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(pairs.get(ReportKey.COUNT_ATTENDANCES)).isEqualTo(1);
    }

    private void markOneToday(AbonnementData abonnementData) {
        AttendanceData attendance = new AttendanceData();
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setAbonnementId(abonnementData.getId());
        markingAttendedFacade.markAttended(DEFAULT_COMPANY_ID, attendance);
    }

    private void markOneYesterday(AbonnementData abonnementData) {
        AbonnementData abonnement = abonnementFacade.findOne(abonnementData.getId());
        AttendanceData attendance = new AttendanceData();
        attendance.setId("2");
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setVisitDate(DateTime.now().minusDays(1));
        abonnement.getAttendances().add(attendance);
        abonnementFacade.save(abonnement);
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
