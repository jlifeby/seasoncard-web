package com.jlife.abon.report;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class Report {

    private String reportName;

    private ReportPeriod reportPeriod;

    private List<ReportEntry> reportEntries;
    private ReportHeader reportHeader;
    private ReportSummary reportSummary;

    public Report() {
        final DateTimeZone tz = DateTimeZone.forID("Europe/Minsk");
        final DateTime now = new DateTime(tz);
        this.reportPeriod = new ReportPeriod(now, now).normalize();
        this.reportEntries = new ArrayList<>();
        this.reportHeader = new ReportHeader();
        this.reportSummary = new ReportSummary();
    }

    public File toExcel() {
        return null;
    }

    public ReportPeriod getReportPeriod() {
        return reportPeriod;
    }

    public void setReportPeriod(ReportPeriod reportPeriod) {
        this.reportPeriod = reportPeriod;
    }

    public List<ReportEntry> getEntries() {
        return reportEntries;
    }

    public void setEntries(List<ReportEntry> entries) {
        this.reportEntries = entries;
    }

    public ReportHeader getReportHeader() {
        return reportHeader;
    }

    public void setReportHeader(ReportHeader reportHeader) {
        this.reportHeader = reportHeader;
    }

    public void addEntry(ReportEntry reportEntry) {
        this.reportEntries.add(reportEntry);
    }

    public List<ReportEntry> getReportEntries() {
        return reportEntries;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public ReportSummary getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(ReportSummary reportSummary) {
        this.reportSummary = reportSummary;
    }
}

