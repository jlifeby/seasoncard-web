package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.security.AcceptingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping(value = "/api/company")
public class AcceptingRestController extends BaseController {

    @Autowired
    private CompanyFacade companyFacade;

    @RequestMapping(value = "/accepted-agreement-and-offer", method = RequestMethod.GET)
    public AcceptingResponse companyIsAcceptedAgreementAndOffer() {
        boolean result = companyFacade.checkCompanyIsAcceptedAgreement(getSessionCompanyId())
                && companyFacade.checkCompanyIsAcceptedOffer(getSessionCompanyId());
        AcceptingResponse acceptingResponse = new AcceptingResponse();
        acceptingResponse.setAccepted(result);
        acceptingResponse.setSuccess(true);
        return acceptingResponse;
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    AcceptingResponse acceptAgreementAndOffer() {
        companyFacade.acceptAgreementAndPublicOffer(getSessionCompanyId());
        AcceptingResponse acceptingResponse = new AcceptingResponse();
        acceptingResponse.setAccepted(true);
        acceptingResponse.setSuccess(true);
        return acceptingResponse;
    }
}
