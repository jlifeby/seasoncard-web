package com.jlife.abon.controller.rest.admin;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.facade.AbonnementFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
// todo added restriction for current logged in employee
@Controller
@RequestMapping("/api")
public class AbonnementRestController extends BaseController {

    @Autowired
    AbonnementFacade abonnementFacade;

    @RequestMapping(value = "/system/abonnement", method = RequestMethod.GET)
    @ResponseBody
    public List<AbonnementData> getAbonnements() {
        return abonnementFacade.findAll();
    }

    @RequestMapping(value = "/system/abonnement/{abonnementId}", method = RequestMethod.GET)
    @ResponseBody
    public AbonnementData getAbonnement(@PathVariable String abonnementId) {
        return abonnementFacade.findOne(abonnementId);
    }

}

