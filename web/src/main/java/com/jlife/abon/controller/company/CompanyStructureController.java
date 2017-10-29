package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyStructureController extends BaseController {

    @Autowired
    public CompanyFacade companyFacade;

    @RequestMapping(value = "/offices")
    public String getCompanyOffices(ModelMap model) {
        model.put("offices", "");
        return "company-offices";
    }

    @RequestMapping(value = "/offices/new")
    public String getCompanyOfficeNew(ModelMap model) {
        model.put("office", "");
        return "company-office-detail";
    }

    @RequestMapping(value = "/offices/{id}")
    public String getCompanyOffice(@PathVariable("id") Long id, ModelMap model) {
        model.put("office", "");
        return "company-office-detail";
    }

    @RequestMapping(value = "/offices/{id}", method = RequestMethod.POST)
    public String postCompanyOffice(@PathVariable("id") Long id, ModelMap model) {
        model.put("office", "");
        return "company-office-detail";
    }


    @RequestMapping(value = "/directions")
    public String getCompanyDirections(ModelMap model) {
        model.put("directions", "");
        return "company-directions";
    }

    @RequestMapping(value = "/directions/new")
    public String getCompanyDirectionNew(ModelMap model) {
        model.put("direction", "");
        return "company-direction-detail";
    }

    @RequestMapping(value = "/directions/{id}")
    public String getCompanyDirection(@PathVariable("id") Long id, ModelMap model) {
        model.put("direction", "");
        return "company-direction-detail";
    }

    @RequestMapping(value = "/directions/{id}", method = RequestMethod.POST)
    public String postCompanyDirection(@PathVariable("id") Long id, ModelMap model) {
        model.put("direction", "");
        return "company-direction-detail";
    }


    @RequestMapping(value = "/groups")
    public String getCompanyGroups(ModelMap model) {
        model.put("groups", "");
        return "company-groups";
    }

    @RequestMapping(value = "/groups/new")
    public String getCompanyGroupNew(ModelMap model) {
        model.put("group", "");
        return "company-group-detail";
    }

    @RequestMapping(value = "/groups/{id}")
    public String getCompanyGroup(@PathVariable("id") Long id, ModelMap model) {
        model.put("group", "");
        return "company-group-detail";
    }

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.POST)
    public String postCompanyGroup(@PathVariable("id") Long id, ModelMap model) {
        model.put("group", "");
        return "company-group-detail";
    }


    @RequestMapping(value = "/employees")
    public String getCompanyEmployees(ModelMap model) {
        model.put("employees", "");
        return "company-employees";
    }

    @RequestMapping(value = "/employees/new")
    public String getCompanyEmployeeNew(ModelMap model) {
        model.put("employee", "");
        return "company-employee-detail";
    }

    @RequestMapping(value = "/employees/{id}")
    public String getCompanyEmployee(@PathVariable("id") Long id,ModelMap model) {
        model.put("employee", "");
        return "company-employee-detail";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.POST)
    public String postCompanyEmployee(@PathVariable("id") Long id,ModelMap model) {
        model.put("employee", "");
        return "company-employee-detail";
    }

}
