package com.jlife.abon.controller.rest.user;

import com.jlife.abon.api.ChangingEmailResponse;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.*;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dzmitry Misiuk
 */
@Controller
@Validated
public class ClientRestController extends BaseController {

    @Autowired
    private ChangingEmailFacade changingEmailFacade;

    @Autowired
    CardFacade cardFacade;

    @Autowired
    CompanyFacade companyFacade;

    @Autowired
    private AbonnementFacade abonnementFacade;

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/api/user/card", method = RequestMethod.GET)
    @ResponseBody
    public CardData getByCardWithFullPopulated() {
        return cardFacade.getCardForUserFullPopulated(getCurrentUser().getId());
    }


    @RequestMapping(value = "/api/user/companies/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public CompanyData getByCompanyWithPopulatedProduct(@PathVariable String companyId) {
        return companyFacade.getCompanyWithActivePublishedProducts(companyId);
    }

    @RequestMapping(value = "/api/user/abonnements/{abonnementId}")
    @ResponseBody
    public AbonnementData getAbonnementWithPopulatedProduct(@PathVariable String abonnementId) {
        return abonnementFacade.getAbonnementByUserCardId(abonnementId, getUserCard().getId());
    }

    @RequestMapping(value = "/api/user/abonnements/consolidated/{productId}")
    @ResponseBody
    public ConsolidatedAbonnementData getConsolidatedAbonnement(@PathVariable("productId") String productId) {
        return abonnementFacade.getConsolidatedAbonnement(getCurrentUser().getId(), productId);
    }

    @RequestMapping(value = "/api/user/profile/update", method = RequestMethod.PATCH)
    @ResponseBody
    public UserData updateUser(@RequestBody UserData user) {
        UserData updatedUser = userFacade.updateOwnProfileAsUser(user, getCurrentUser().getId());
        getApplication().updateCurrentUserInSession();
        return updatedUser;
    }

    @RequestMapping(value = "/api/user/settings/update", method = RequestMethod.PATCH)
    @ResponseBody
    public UserData updateUserSettings(@RequestBody UserSettingsData userSettings) {
        UserData updatedUser = userFacade.updateOwnSettingsAsUser(userSettings, getCurrentUser().getId());
        getApplication().updateCurrentUserInSession();
        return updatedUser;
    }

    @RequestMapping(value = "/api/user/change-email-with-agreement", method = RequestMethod.POST)
    @ResponseBody
    public ChangingEmailResponse changeEmailWithConfirmation(@NotBlank @Email @RequestParam("email") String email) {
        ChangingEmailResponse response = new ChangingEmailResponse();
        try {
            changingEmailFacade.changeEmailWithAcceptingAgreement(getCurrentUser().getId(), email);
            response.setSuccess(true);
            return response;
        } catch (AbonRuntimeException e) {
            response.setSuccess(false);
            response.setApiErrorCode(e.getApiErrorCode());
            response.setArguments(e.getArguments());
            response.setMsg(e.getMessage());
            return response;
        }
    }
}
