package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.enumeration.ReportFilterType;
import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.CompanyTrainerFacade;
import com.jlife.abon.facade.ReportFacade;
import com.jlife.abon.form.ReportFilterForm;
import com.jlife.abon.report.Report;
import com.jlife.abon.service.ExcelGeneratorService;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company/reports")
@Secured({"ROLE_COMPANY"})
public class CompanyReportController extends BaseController {

    @Autowired
    public CompanyFacade companyFacade;
    @Autowired
    public CompanyTrainerFacade companyTrainerFacade;
    @Autowired
    private ExcelGeneratorService excelGeneratorService;
    @Autowired
    private ReportFacade reportFacade;

    //TODO Filter validation
    private void setFilterData(ModelMap model) {
        model.put("filterTypeValues", ReportFilterType.values());
        model.put("reportFilterForm", new ReportFilterForm());
    }

    @RequestMapping(value = "/products")
    public String getProductReport(ModelMap model) {

        setFilterData(model);

        return "report-products";
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String generateProductReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                        ModelMap model) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForAllAbonnements(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for all abonnements. ", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
        return "report-products";
    }

    @RequestMapping(value = "/products/export", method = RequestMethod.POST)
    public void generateExcelProductReport(Model model,
                                           @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                           HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForAllAbonnements(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for all abonnements. ", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }


    @RequestMapping(value = "/sold-products")
    public String getSoldProductReport(ModelMap model) {
        setFilterData(model);
        return "report-sold-products";
    }

    @RequestMapping(value = "/sold-products", method = RequestMethod.POST)
    public String generateSoldProductReport(ModelMap model,
                                            @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForAllSoldAbonnements(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for all sold abonnements. ", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
        return "report-sold-products";
    }

    @RequestMapping(value = "/sold-products/export", method = RequestMethod.POST)
    public void generateExcelSoldProductReport(ModelMap model,
                                               @Valid @ModelAttribute("reportFilterForm") ReportFilterForm
                                                       reportFilterForm,
                                               HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForAllSoldAbonnements(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for sold abonnements", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
            return;
        }

    }


    @RequestMapping(value = "/clients")
    public String getClientReport(ModelMap model) {

        setFilterData(model);

        return "report-clients";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public String generateClientReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm
                                               reportFilterForm,
                                       ModelMap model) {

        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForNewClients(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }

        return "report-clients";
    }

    @RequestMapping(value = "/clients/export", method = RequestMethod.POST)
    public void generateExcelClientReport(Model model,
                                          @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                          HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForNewClients(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }

    @RequestMapping(value = "/clients-full")
    public String getClientFullReport(ModelMap model) {

        setFilterData(model);

        return "report-clients-full";
    }

    @RequestMapping(value = "/clients-full", method = RequestMethod.POST)
    public String generateClientFullReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm
                                                   reportFilterForm,
                                           ModelMap model) {

        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForAllClients(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for all clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }

        return "report-clients-full";
    }

    @RequestMapping(value = "/clients-full/export", method = RequestMethod.POST)
    public void generateExcelClientFullReport(Model model,
                                              @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                              HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForAllClients(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for all clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }


    @RequestMapping(value = "/visits-by-time")
    public String getVisitsByTimeReport(ModelMap model) {
        setFilterData(model);
        return "report-visits-by-time";
    }

    @RequestMapping(value = "/visits-by-time", method = RequestMethod.POST)
    public String generateVisitsByTimeReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm
                                                     reportFilterForm, ModelMap model) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForVisitsByTime(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for visits by time", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }

        return "report-visits-by-time";
    }

    @RequestMapping(value = "/visits-by-time/export", method = RequestMethod.POST)
    public void generateExcelVisitsByTimeReport(Model model,
                                                @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                                HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForVisitsByTime(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for visits by time", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }


    @RequestMapping(value = "/birthday")
    public String getBirthdayReport(ModelMap model) {

        setFilterData(model);

        return "report-birthday";
    }

    @RequestMapping(value = "/birthday", method = RequestMethod.POST)
    public String generateBirthdayReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                         ModelMap model) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForBirthday(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }

        return "report-birthday";
    }

    @RequestMapping(value = "/birthday/export", method = RequestMethod.POST)
    public void generateExcelBirthdayReport(Model model,
                                            @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                            HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForBirthday(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }

    @RequestMapping(value = "/single-attendance")
    public String getSingleAttendanceReport(ModelMap model) {
        setFilterData(model);
        return "report-single-attendance";
    }

    @RequestMapping(value = "/single-attendance", method = RequestMethod.POST)
    public String generateSingleAttendanceReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                                 ModelMap model) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForSingleAttendance(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }

        return "report-single-attendance";
    }

    @RequestMapping(value = "/single-attendance/export", method = RequestMethod.POST)
    public void generateExcelSingleAttendanceReport(Model model,
                                                    @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                                    HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForSingleAttendance(getSessionCompanyId(), reportFilterForm.getReportPeriod());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }

    @RequestMapping(value = "/trainer")
    public String getTrainerReport(ModelMap model) {
        setFilterData(model);
        model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
        model.put("reportTrainerTypes", ReportTrainerType.values());
        return "report-trainer";
    }

    @RequestMapping(value = "/trainer", method = RequestMethod.POST)
    public String generateTrainerReport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                        ModelMap model) {
        try {
            setFilterData(model);
            model.put("reportFilterForm", reportFilterForm);
            Report report = reportFacade.generateReportForTrainer(getSessionCompanyId(),
                    reportFilterForm.getReportPeriod(), reportFilterForm.getTrainerId(), reportFilterForm.getReportTrainerType());
            model.put("report", report);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
        model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
        model.put("reportTrainerTypes", ReportTrainerType.values());
        return "report-trainer";
    }

    @RequestMapping(value = "/trainer/export", method = RequestMethod.POST)
    public void generateExcelTrainerReport(Model model,
                                           @Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                                           HttpServletResponse response) {
        try {
            Report report = reportFacade.generateReportForTrainer(getSessionCompanyId(),
                    reportFilterForm.getReportPeriod(), reportFilterForm.getTrainerId(), reportFilterForm.getReportTrainerType());
            sendFile(report, response);
        } catch (AbonRuntimeException e) {
            LOG.error("Error generating report for new clients", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("reportFilterForm", reportFilterForm);
        }
    }


    private void sendFile(Report report, HttpServletResponse response) {
        try {

            Workbook workbook = excelGeneratorService.generateExcel(report);
            String dateLabel = DateTime.now(DateTimeZone.forID("Europe/Minsk")).toString("dd_MM_yyyy_HH_mm");
            String reportName = report.getReportName().replaceAll("[-+.^:,() ]", "_");
            String fileName = reportName + "_" + dateLabel + ".xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + URLEncoder.encode(fileName, "UTF-8") + "\"");
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("Error sending report: ", e);
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, "Error Sending file");
        }
    }
}
