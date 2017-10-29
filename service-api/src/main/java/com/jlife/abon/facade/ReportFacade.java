package com.jlife.abon.facade;

import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportPeriod;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ReportFacade {
    Report generateReportForAllAbonnements(String companyId, ReportPeriod reportPeriod);

    Report generateReportForNewClients(String companyId, ReportPeriod reportPeriod);

    Report generateReportForAllClients(String companyId, ReportPeriod reportPeriod);

    Report generateReportForAllSoldAbonnements(String companyId, ReportPeriod reportPeriod);

    Report generateReportForVisitsByTime(String companyId, ReportPeriod reportPeriod);

    Report generateReportForBirthday(String companyId, ReportPeriod reportPeriod);

    Report generateReportForSingleAttendance(String companyId, ReportPeriod reportPeriod);

    Report generateReportForTrainer(String companyId, ReportPeriod reportPeriod, String trainerId, ReportTrainerType type);
}
