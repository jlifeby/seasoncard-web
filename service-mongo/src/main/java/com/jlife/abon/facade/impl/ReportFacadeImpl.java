package com.jlife.abon.facade.impl;

import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.facade.ReportFacade;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportPeriod;
import com.jlife.abon.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class ReportFacadeImpl extends AbstractFacade implements ReportFacade {

    @Autowired
    private ReportService reportService;

    @Override public Report generateReportForAllAbonnements(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForAllAbonnements(companyId, reportPeriod);
    }

    @Override public Report generateReportForNewClients(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForNewClients(companyId, reportPeriod);
    }

    @Override
    public Report generateReportForAllClients(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForAllClients(companyId, reportPeriod);
    }

    @Override public Report generateReportForAllSoldAbonnements(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForAllSoldAbonnements(companyId, reportPeriod);
    }

    @Override public Report generateReportForVisitsByTime(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForAllAttendances(companyId, reportPeriod);
    }

    @Override public Report generateReportForBirthday(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForBirthday(companyId, reportPeriod);
    }

    @Override public Report generateReportForSingleAttendance(String companyId, ReportPeriod reportPeriod) {
        return reportService.generateReportForSingleAttendance(companyId, reportPeriod);
    }

    @Override public Report generateReportForTrainer(String companyId, ReportPeriod reportPeriod, String trainerId, ReportTrainerType type) {
        return reportService.generateReportForTrainer(companyId, reportPeriod, trainerId, type);
    }
}
