package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.security.WhoIAmResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/api/whoiam")
public class WhoIAmController extends BaseController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public WhoIAmResponse getLoginUser() {
        WhoIAmResponse whoIAmResponse = new WhoIAmResponse();
        UserData user = getCurrentUser();
        if (user != null) {
            whoIAmResponse.setAuthenticated(true);
            whoIAmResponse.setName(user.getName());
            whoIAmResponse.setRoles(user.getRoles());
            whoIAmResponse.setId(user.getId());
            whoIAmResponse.setEmail(user.getEmail());
            whoIAmResponse.setLastName(user.getLastName());
        } else {
            whoIAmResponse.setAuthenticated(false);
        }
        return whoIAmResponse;
    }
}
