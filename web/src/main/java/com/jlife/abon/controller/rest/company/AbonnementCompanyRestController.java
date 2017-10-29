package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.dto.ConsolidatedAbonnementData;
import com.jlife.abon.facade.AbonnementFacade;
import com.jlife.abon.facade.MarkingAttendedFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dzmitry Misiuk
 */
// todo added restriction for current logged in employee
@RestController
@RequestMapping("/api/company/")
public class AbonnementCompanyRestController extends BaseController {

    @Autowired
    AbonnementFacade abonnementFacade;

    @Autowired
    private MarkingAttendedFacade markingAttendedFacade;

    @RequestMapping(value = {"/abonnement/{abonnementId}", "/abonnements/{abonnementId}"}, method = RequestMethod.GET)
    public AbonnementData getAbonnementForCompany(@PathVariable String abonnementId) {
        String companyId = getSessionCompanyId();
        return abonnementFacade.getAbonnementByCompany(abonnementId, companyId);
    }

    @RequestMapping(value = "/abonnements/consolidated", method = RequestMethod.GET)
    public ConsolidatedAbonnementData getConsolidatedAbonnement(@RequestParam(name = "productId", required = true) String productId,
                                                                @RequestParam(name = "userId", required = true) String userId) {
        return abonnementFacade.getConsolidatedAbonnementAsCompany(userId, productId, getSessionCompanyId());
    }


    @RequestMapping(value = {"/abonnement/markAttended", "/abonnements/mark-attended"}, method = RequestMethod.POST)
    public AbonnementData markAttended(@RequestParam("abonnementId") String abonnementId,
                                       @RequestParam(value = "markUnits", defaultValue = "1") int markUnits) {
        return abonnementFacade.markAttended(abonnementId, getSessionCompanyId(), markUnits);
    }

    // todo check for using
    @RequestMapping(value = {"/abonnement/markAttendedByCardId", "/abonnements/mark-attended-by-card-id"},
            method = RequestMethod.POST)
    public AbonnementData markAttendedByCardId(@RequestParam("cardId") String cardId,
                                               @RequestParam("abonnementId") String abonnementId,
                                               @RequestParam(value = "markUnits", defaultValue = "1") int markUnits) {
        return abonnementFacade.markAttended(abonnementId, getSessionCompanyId(), markUnits);
    }

    @RequestMapping(value = "/abonnements/change-end-date", method = RequestMethod.POST)
    public AbonnementData changeEndDate(@RequestParam(name = "abonnementId", required = true) String abonnementId,
                                        @RequestParam(name = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") DateTime endDate,
                                        @RequestParam(value = "comment", required = false) String comment) {
        return abonnementFacade.changeEndDate(abonnementId, endDate, comment, getSessionCompanyId());
    }

    @RequestMapping(value = "/abonnements/add-comment", method = RequestMethod.POST)
    public AbonnementData addComment(@RequestParam(name = "abonnementId", required = true) String abonnementId,
                                     @RequestParam(value = "comment", required = true) String comment) {
        return abonnementFacade.addComment(abonnementId, comment, getSessionCompanyId());
    }

    @PostMapping(value = {"/abonnements/mark-attended"}, consumes = "application/json")
    public AbonnementData markAttendedByAttendance(@RequestBody AttendanceData attendance) {
        return markingAttendedFacade.markAttended(getSessionCompanyId(), attendance);
    }
}

