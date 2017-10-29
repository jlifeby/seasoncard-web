package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.WidgetSettingData;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.PotentialClientFacade;
import com.jlife.abon.facade.WidgetSettingFacade;
import com.jlife.abon.form.PotentialClientForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Controller
public class ExternalEnrollPotentialClientController extends BaseController {


    @Autowired
    private WidgetSettingFacade widgetSettingFacade;

    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private PotentialClientFacade potentialClientFacade;

    @Value("classpath:widget/widget.js")
    private Resource widgetJS;

    @Value("${app.url}")
    private String appUrl;


    @RequestMapping(value = "/ext/{companyId}/widget.js", produces = {"application/javascript; charset=utf-8"})
    @ResponseBody
    public String getWidgetJS(@PathVariable("companyId") String companyId) {

        try {
            WidgetSettingData widgetSetting = widgetSettingFacade.getWidgetSettingForCompany(companyId);

            String jsFile = IOUtils.toString(widgetJS.getInputStream(), "UTF-8");

            jsFile = jsFile.replace("${appUrl}", appUrl);
            jsFile = jsFile.replace("${widgetId}", widgetSetting.getId());
            jsFile = jsFile.replace("${buttonText}", widgetSetting.getButtonText());
            jsFile = jsFile.replace("${showButton}", String.valueOf(widgetSetting.isShowButton()));
            jsFile = jsFile.replace("${buttonPosition}", widgetSetting.getButtonPosition());
            jsFile = jsFile.replace("${buttonColor}", widgetSetting.getButtonColor());
            jsFile = jsFile.replace("${buttonAnimation}", String.valueOf(widgetSetting.isButtonAnimation()));
            jsFile = jsFile.replace("${formPosition}", widgetSetting.getFormPosition());
            jsFile = jsFile.replace("${companyId}", companyId);

            return jsFile;
        } catch (IOException e) {
            LOG.error("error on reading widget file from fs", e);
            return "";
        }
    }

    @RequestMapping(value = "/ext/{companyId}/widget")
    public String getWidgetHTML(@PathVariable("companyId") String companyId,
                                ModelMap model) {

        WidgetSettingData widgetSetting = widgetSettingFacade.getWidgetSettingForCompany(companyId);

        model.put("potentialClientForm", new PotentialClientForm());
        model.put("widgetSetting", widgetSetting);
        model.put("widgetCompany", companyFacade.getCompany(widgetSetting.getCompanyId()));

        return "widget";
    }

    @PostMapping("/ext/widget/enroll")
    public String enrollPotentialClient(ModelMap model,
                                        @NotNull @ModelAttribute("companyId") String companyId,
                                        @Valid @ModelAttribute("potentialClientForm") PotentialClientForm potentialClientForm,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("potentialClientForm", potentialClientForm);
            return "widget";
        }

        WidgetSettingData widgetSetting = widgetSettingFacade.getWidgetSettingForCompany(companyId);
        CompanyData widgetCompany = companyFacade.getCompany(widgetSetting.getCompanyId());
        if (companyId.equals(getSessionCompanyId())) {
            model.addAttribute(ERROR_MESSAGE, "Нельзя выполнить запись клиента в демо-режиме");
            model.addAttribute("potentialClientForm", potentialClientForm);
            model.put("widgetSetting", widgetSetting);
            model.put("widgetCompany", widgetCompany);
            return "widget";
        }
        try {
            ClientData clientData = potentialClientForm.toClientData();
            potentialClientFacade.selfRegisterClient(clientData, companyId);

            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Запись успешно выполнена!");

            // todo change finishing page

            return "redirect:/ext/" + companyId + "/widget";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on enrolling potential client ", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("potentialClientForm", potentialClientForm);
            model.put("widgetSetting", widgetSetting);
            model.put("widgetCompany", widgetCompany);
            return "widget";
        }
    }

}
