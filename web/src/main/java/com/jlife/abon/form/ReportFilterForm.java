package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.ReportFilterType;
import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.report.ReportPeriod;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class ReportFilterForm implements Serializable {

    private ReportFilterType reportFilterType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;

    private String trainerId;
    private ReportTrainerType reportTrainerType;

    public ReportFilterForm() {
        this.reportFilterType = ReportFilterType.BY_DAY;
    }

    public ReportFilterType getReportFilterType() {
        return reportFilterType;
    }

    public void setReportFilterType(ReportFilterType reportFilterType) {
        this.reportFilterType = reportFilterType;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public ReportTrainerType getReportTrainerType() {
        return reportTrainerType;
    }

    public void setReportTrainerType(ReportTrainerType reportTrainerType) {
        this.reportTrainerType = reportTrainerType;
    }

    final private DateTimeZone minskTimeZone = DateTimeZone.forID("Europe/Minsk");

    public DateTime getReportStartDate() {
        if (reportFilterType.equals(ReportFilterType.BY_DAY)) {
            return new DateTime(minskTimeZone);
        } else if (reportFilterType.equals(ReportFilterType.BY_MONTH)) {
            return new DateTime(minskTimeZone).withDayOfMonth(1);
        } else if (reportFilterType.equals(ReportFilterType.BY_CUSTOM)) {
            return startDate.toDateTime(minskTimeZone);
        }
        return startDate;
    }

    public DateTime getReportEndDate() {
        switch (reportFilterType) {
            case BY_DAY:
                return new DateTime(minskTimeZone);
            case BY_MONTH:
                return endOfMonthForDate(new DateTime(minskTimeZone));
            case BY_CUSTOM:
                return endDate.toDateTime(minskTimeZone);
            default:
                throw new EnumConstantNotPresentException(reportFilterType.getClass(), reportFilterType.toString());
        }
    }

    public String getReportTimeLabel() {
        if (reportFilterType.equals(ReportFilterType.BY_DAY)) {
            return String.format("За текущий день: %s", getReportEndDate().toString("d.MM.yyyy"));
        } else if (reportFilterType.equals(ReportFilterType.BY_MONTH)) {
            return String.format("За текущий месяц: %s", getReportEndDate().toString("d.MM.yyyy"));
        }
        if (reportFilterType.equals(ReportFilterType.BY_CUSTOM)) {
            return String.format("За период: %s - %s", getStartDate().toString("d.MM.yyyy"), getEndDate().toString("d.MM.yyyy"));
        }
        return "";
    }

    public ReportPeriod getReportPeriod() {
        return new ReportPeriod(getReportStartDate(), getReportEndDate()).normalize();
    }

    private DateTime endOfMonthForDate(DateTime dateTime) {
        return dateTime.plusMonths(1).withDayOfMonth(1).minusDays(1);
    }
}
