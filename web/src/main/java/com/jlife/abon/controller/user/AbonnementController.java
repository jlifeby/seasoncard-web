package com.jlife.abon.controller.user;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.ConsolidatedAbonnementData;
import com.jlife.abon.facade.AbonnementFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.PromotionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@Secured({"ROLE_USER"})
public class AbonnementController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AbonnementController.class);

    @Autowired
    private AbonnementFacade abonnementFacade;

    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private PromotionFacade promotionFacade;


    @RequestMapping(value = "/user/abonnements")
    public String getMyAbonnements(ModelMap model) {

        model.put("abons", abonnementFacade.getLastAbonnementsForUser(getCurrentUser().getId()));

        return "user-abons";
    }

    @RequestMapping(value = "/user/abonnements/{abonnementId}")
    public String getMyAbonnement(ModelMap model,
                                  @PathVariable("abonnementId") String abonnementId) {
        model.put("abon", abonnementFacade.getAbonnementByUserCard(abonnementId, getUserCard().getId()));
        return "user-abon";
    }

    @RequestMapping(value = "/user/company")
    public String getMyCompany(ModelMap model) {
        String companyId = getApplication().getCurrentCard().getInitializingCompany();
        model.put("company", companyFacade.getCompanyWithActivePublishedProducts(companyId));
        model.put("promotions", promotionFacade.getPublishedActiveActualPromotionsForCompany(companyId));
        return "company-view";
    }

    @RequestMapping(value = "/user/abonnements/{productId}/history")
    public String getAbonnementHistory(ModelMap model,
                                       @PathVariable("productId") String productId) {

        ConsolidatedAbonnementData consolidatedAbonnementData = abonnementFacade.getConsolidatedAbonnement(getCurrentUser().getId(), productId);

        Collections.sort(consolidatedAbonnementData.getAllAbonnements(), new Comparator<AbonnementData>() {
            public int compare(AbonnementData o1, AbonnementData o2) {
                return o1.getPurchaseDate().compareTo(o2.getPurchaseDate());
            }
        });
        Collections.reverse(consolidatedAbonnementData.getAllAbonnements());

        model.put("consolidatedAbonnementData", consolidatedAbonnementData);

        return "user-abon-history";
    }

}
