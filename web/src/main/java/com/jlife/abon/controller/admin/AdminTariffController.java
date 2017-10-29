package com.jlife.abon.controller.admin;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.facade.TariffFacade;
import com.jlife.abon.form.TariffForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Aliaksei Pashkouski
 * @author Dzmitry Misiuk
 */

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminTariffController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTariffController.class);

    @Autowired
    public TariffFacade tariffFacade;

    @RequestMapping(value = "/tariffs")
    public String getAllTariffs(ModelMap model) {
        model.put("tariffs", tariffFacade.getAllTariffs());
        return "admin-tariffs";
    }

    @RequestMapping(path = "/tariffs/new")
    public String newTariff(ModelMap model) {
        model.put("tariffForm", new TariffForm());
        return "admin-tariff-detail";
    }

    @RequestMapping(path = "/tariffs/{tariffId}/detail")
    public String getTariff(ModelMap model, @PathVariable String tariffId) {
        model.put("tariffForm", TariffForm.toTariffForm(tariffFacade.getTariff(tariffId)));
        return "admin-tariff-detail";
    }

    @RequestMapping(value = "/tariffs/detail", method = RequestMethod.POST)
    public String saveTariff(ModelMap model,
                             @ModelAttribute("tariffForm") TariffForm tariffForm,
                             RedirectAttributes redirectAttributes) {

        TariffData tariffData = tariffForm.toTariffData();

        String msg;
        if (tariffData.getId() != null) {
            tariffFacade.updateTariff(tariffData);
            msg = String.format("Тариф \"%s\" успешно обновлен!", tariffData.getName());
        } else {
            tariffFacade.createTariff(tariffData);
            msg = String.format("Тариф \"%s\" успешно создан!", tariffData.getName());
        }
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
        return "redirect:/admin/tariffs";
    }
}
