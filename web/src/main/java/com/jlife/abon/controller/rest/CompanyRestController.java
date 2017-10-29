package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dzmitry Misiuk
 * @author Aliaksei Pashkouski
 */
@Controller
@RequestMapping("/api")
public class CompanyRestController extends BaseController {

    @Autowired
    private CompanyFacade companyFacade;

    @RequestMapping(value = "/system/company/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public CompanyData getCompany(@PathVariable String companyId) {
        return companyFacade.getCompany(companyId);
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    public CompanyData getCompany() {
        return companyFacade.getCompany(getSessionCompanyId());
    }


}
