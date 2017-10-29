package com.jlife.abon.controller.admin;

import com.jlife.abon.controller.application.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminAnalyticController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AdminAnalyticController.class);

    @GetMapping(value = "/analytics")
    public String getAnalyticPage() {
        return "admin-analytics";
    }
}
