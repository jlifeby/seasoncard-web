package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.WidgetSettingData;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.WidgetSettingFacade;
import com.jlife.abon.form.WidgetSettingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@Controller
@Secured({"ROLE_COMPANY"})
public class WidgetCompanyController extends BaseController {

    @Autowired
    private WidgetSettingFacade widgetSettingFacade;

    @GetMapping(value = "/company/widget/design")
    public String getWidgetDesign(ModelMap model) {

        WidgetSettingData widgetSetting = widgetSettingFacade.getWidgetSettingForCompany(getSessionCompanyId());

        if (widgetSetting == null) {
            WidgetSettingData newWidgetSetting = new WidgetSettingForm().toWidgetSetting();
            newWidgetSetting.setCompanyId(getSessionCompanyId());
            newWidgetSetting.setActive(true);
            widgetSetting = widgetSettingFacade.create(newWidgetSetting, getSessionCompanyId());
        }
        model.put("widgetSettingForm", new WidgetSettingForm(widgetSetting));

        return "company-widget-design";
    }

    @PostMapping("/company/widget/design")
    public String saveProduct(Model model,
                              @Valid @ModelAttribute("widgetSettingForm") WidgetSettingForm widgetSettingForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("widgetSettingForm", widgetSettingForm);
            return "company-widget-design";
        }
        WidgetSettingData widgetSettingData = widgetSettingForm.toWidgetSetting();
        try {

            if (widgetSettingData.getId() == null) {
                widgetSettingData.setCompanyId(getSessionCompanyId());
                widgetSettingData.setActive(true);
                widgetSettingFacade.create(widgetSettingData, getSessionCompanyId());
            } else {
                widgetSettingFacade.update(widgetSettingData.getId(), widgetSettingData, getSessionCompanyId());
            }
            String msg = "Настройки онлайн записи сохранены";
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
            return "redirect:/company/widget/design";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving widgetSetting ", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("widgetSettingForm", widgetSettingForm);
            return "company-widget-design";
        }
    }

    @GetMapping("/company/widget/code")
    public String getWidgetCode(ModelMap model) {
        WidgetSettingData widgetSetting = widgetSettingFacade.getWidgetSettingForCompany(getSessionCompanyId());

        if (widgetSetting != null) {
            model.put("widgetSettingForm", new WidgetSettingForm(widgetSetting));
        } else {
            model.put("widgetSettingForm", new WidgetSettingForm());
        }

        return "company-widget-code";
    }

}
