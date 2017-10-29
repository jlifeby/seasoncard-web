package com.jlife.abon.service.impl;

import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportEntry;
import com.jlife.abon.report.ReportHeader;
import com.jlife.abon.report.ReportSummary;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.ExcelGeneratorService;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class ExcelGeneratorServiceImpl extends AbstractService implements ExcelGeneratorService {

    protected CellStyle cs = null;
    protected CellStyle csBold = null;
    protected CellStyle csTop = null;
    protected CellStyle csRight = null;
    protected CellStyle csBottom = null;
    protected CellStyle csLeft = null;

    protected CellStyle csBottomBold = null;
    protected CellStyle csCenterBold = null;
    protected CellStyle csAlignCenterWrap = null;


    @Override public Workbook generateExcel(Report report) throws IOException {
        Workbook workbook = createExcel(report);
        return workbook;
    }

    private Workbook createExcel(Report report) {

        try {

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(report.getReportName());

            //Setup some styles that we need for the Cells
            setCellStyles(wb);

            //Setup the Page margins - Left, Right, Top and Bottom
            sheet.setMargin(Sheet.LeftMargin, 0.25);
            sheet.setMargin(Sheet.RightMargin, 0.25);
            sheet.setMargin(Sheet.TopMargin, 0.75);
            sheet.setMargin(Sheet.BottomMargin, 0.75);

            //Setup the Header and Footer Margins
            sheet.setMargin(Sheet.HeaderMargin, 0.25);
            sheet.setMargin(Sheet.FooterMargin, 0.25);

            //Set Header Information
            Header header = sheet.getHeader();
            header.setLeft("SeasonCard");
            header.setCenter(HSSFHeader.font("Arial", "Bold") +
                    HSSFHeader.fontSize((short) 14) + report.getReportName());
            String reportTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).toString("dd.MM.yyyy HH:mm");
            header.setRight(reportTime);

            //Set Footer Information with Page Numbers
            Footer footer = sheet.getFooter();
            //footer.setRight( "Page " + HeaderFooter.page() + " of " +
            //        HeaderFooter.numPages() );
            footer.setRight(HeaderFooter.page());


            setColumnWidth(sheet);

            int rowIndex = 0;
            rowIndex = insertReportHeader(sheet, report);
            rowIndex = insertReportTable(sheet, report, rowIndex);

            return wb;
        } catch (Exception e) {
            LOG.error("Error generating excel: ", e);
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
    }

    private void setCellStyles(Workbook wb) {

        //font size 10
        Font f = wb.createFont();
        f.setFontHeightInPoints((short) 10);

        //Simple style
        cs = wb.createCellStyle();
        cs.setFont(f);

        //Bold Fond
        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        bold.setFontHeightInPoints((short) 10);

        //Bold style
        csBold = wb.createCellStyle();
        csBold.setFont(bold);

        //Bold style
        csBottomBold = wb.createCellStyle();
        csBottomBold.setBorderBottom(CellStyle.BORDER_THIN);
        csBottomBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBottomBold.setFont(bold);

        //Bold style
        csCenterBold = wb.createCellStyle();
        csCenterBold.setBorderTop(CellStyle.BORDER_THIN);
        csCenterBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csCenterBold.setFont(bold);
        csCenterBold.setAlignment(CellStyle.ALIGN_CENTER);
        csCenterBold.setWrapText(true);

        //Setup style for Top Border Line
        csTop = wb.createCellStyle();
        csTop.setBorderTop(CellStyle.BORDER_THIN);
        csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
        csTop.setFont(f);

        //Setup style for Right Border Line
        csRight = wb.createCellStyle();
        csRight.setBorderRight(CellStyle.BORDER_THIN);
        csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
        csRight.setFont(f);

        //Setup style for Bottom Border Line
        csBottom = wb.createCellStyle();
        csBottom.setBorderBottom(CellStyle.BORDER_THIN);
        csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBottom.setFont(f);

        //Setup style for Left Border Line
        csLeft = wb.createCellStyle();
        csLeft.setBorderLeft(CellStyle.BORDER_THIN);
        csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        csLeft.setFont(f);

        //Setup style for Left Border Line
        csAlignCenterWrap = wb.createCellStyle();
        csAlignCenterWrap.setAlignment(CellStyle.ALIGN_CENTER);
        csAlignCenterWrap.setWrapText(true);
        csAlignCenterWrap.setFont(f);
    }

    private int insertReportHeader(Sheet sheet, Report report) {

        int rowIndex = 0;
        Row row = null;
        Cell c = null;

        //rowIndex++;
        row = sheet.createRow(rowIndex);
        c = row.createCell(0);
        c.setCellValue(report.getReportName());
        c.setCellStyle(csBold);

        rowIndex++;
        row = sheet.createRow(rowIndex);
        c = row.createCell(0);
        DateTime startDate = report.getReportPeriod().getStartDate();
        DateTime endDate = report.getReportPeriod().getEndDate();
        String formattedStartDate = startDate.toDateTime(DateTimeZone.forID("Europe/Minsk")).toString("dd.MM.yyyy HH:mm");
        String formattedEndDate = endDate.toDateTime(DateTimeZone.forID("Europe/Minsk")).toString("dd.MM.yyyy HH:mm");
        String periodLabel = "Период: " + formattedStartDate + " - " + formattedEndDate;
        c.setCellValue(periodLabel);
        c.setCellStyle(csBold);

        rowIndex++;

        return rowIndex;

    }

    protected void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 4000);
    }

    protected int insertReportTable(Sheet sheet, Report report, int rowIndex) {

        rowIndex++;

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(rowIndex);
        int headerIt = 0;
        ReportHeader reportHeader = report.getReportHeader();
        for (Map.Entry<Object, Object> headerEntry : reportHeader.getPairs().entrySet()) {
            String headerCellTitle = headerEntry.getValue().toString();
            cell = row.createCell(headerIt);
            cell.setCellValue(headerCellTitle);
            cell.setCellStyle(csBottomBold);
            cell.getCellStyle().setWrapText(true);

            headerIt++;
        }

        for (ReportEntry reportEntry : report.getEntries()) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            int cellIt = 0;
            for (Map.Entry<Object, Object> headerEntry : reportHeader.getPairs().entrySet()) {
                String cellValue = String.valueOf(reportEntry.getValue(headerEntry.getKey()));
                cell = row.createCell(cellIt);
                cell.setCellValue(cellValue);
                cell.setCellStyle(csAlignCenterWrap);
                cellIt++;
            }
        }

        rowIndex++;
        row = sheet.createRow(rowIndex);
        int summaryIt = 0;
        ReportSummary reportSummary = report.getReportSummary();
        for (Map.Entry<Object, Object> headerEntry : reportSummary.getPairs().entrySet()) {
            String headerCellTitle = headerEntry.getValue().toString();
            cell = row.createCell(summaryIt);
            cell.setCellValue(headerCellTitle);
            cell.setCellStyle(csCenterBold);
            cell.getCellStyle().setWrapText(true);
            summaryIt++;
        }

        return rowIndex;

    }
}
