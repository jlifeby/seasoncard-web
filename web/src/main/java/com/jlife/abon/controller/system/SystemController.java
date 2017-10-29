package com.jlife.abon.controller.system;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/system")
// todo security
public class SystemController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    public UserFacade userFacade;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getSystemMainPage(ModelMap model) {
        return "system";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(ModelMap model) {
        List<UserData> users = userFacade.getAllUsers();
        model.put("users", users);
        return "system-users";
    }

}
