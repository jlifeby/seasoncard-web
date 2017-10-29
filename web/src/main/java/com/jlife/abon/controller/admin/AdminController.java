package com.jlife.abon.controller.admin;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.facade.ProductFacade;
import com.jlife.abon.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Aliaksei Pashkouski
 * @author Dzmitry Misiuk
 */

@Controller
@Secured({"ROLE_ADMIN"})
public class AdminController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public CardFacade cardFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public ProductFacade productFacade;

    @RequestMapping(value = "/admin/cards")
    public String getAllCards(ModelMap model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "100") int size) {

        //TODO DM Card pagination
        List<CardData> allCards = cardFacade.getAllCards();

        Predicate<CardData> isFree = new Predicate<CardData>() {
            @Override
            public boolean apply(CardData input) {
                return input.isFree();
            }
        };
        int countOfFreeCard = Collections2.filter(allCards, isFree).size();
        int countOfAllCard = allCards.size();
        model.put("cards", allCards.subList((page - 1) * size, countOfAllCard > page * size ? page * size : countOfAllCard - 1));
        model.put("countOfFreeCard", countOfFreeCard);
        model.put("countOfAllCard", countOfAllCard);

        model.put("page", page);
        model.put("totalPages", countOfAllCard / size);

        return "admin-cards";
    }

    @RequestMapping(value = "/admin/users")
    public String getAllUsers(ModelMap model) {
        List<UserData> users = userFacade.getAllUsers();
        model.put("users", users);
        return "admin-users";
    }

    @RequestMapping(value = "/admin/sms")
    public String getSMS(ModelMap model) {
        return "admin-sms";
    }

    @RequestMapping(value = "/admin/sms", method = RequestMethod.POST)
    public String sendSMS(@ModelAttribute("phone") String phone,
                          @ModelAttribute("textMessage") String textMessage,
                          ModelMap model) {

        //TODO DM Add email send service

        model.addAttribute(ERROR_MESSAGE, "Сообщение отправлено");
        return "admin-sms";
    }

}
