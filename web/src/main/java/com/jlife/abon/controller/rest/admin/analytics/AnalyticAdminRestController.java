package com.jlife.abon.controller.rest.admin.analytics;

import by.jlife.analytics.dto.AnalyticItem;
import by.jlife.analytics.dto.AnalyticsRequest;
import by.jlife.analytics.dto.HourAttendance;
import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.service.AnalyticService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api/admin/analytics/")
public class AnalyticAdminRestController extends BaseController {

    @Autowired
    AnalyticService analyticService;

    @GetMapping("/visit-by-hours")
    public List<HourAttendance> getAttendancesByHours() {
        AnalyticsRequest analyticsRequest = new AnalyticsRequest();
        analyticsRequest.setStartDate(new DateTime().minusMonths(1));
        return analyticService.getForAttendances(analyticsRequest.getStartDate(), analyticsRequest.getEndDate());
    }

    @GetMapping("/new-clients-by-hours")
    public List<AnalyticItem> getNewClientsByHours() {
        AnalyticsRequest analyticsRequest = new AnalyticsRequest();
        analyticsRequest.setStartDate(new DateTime().minusMonths(1));
        return analyticService.getForNewClients(analyticsRequest.getStartDate(), analyticsRequest.getEndDate());
    }

    @GetMapping("/sold-products-by-hours")
    public List<AnalyticItem> getSoldProductsByHours() {
        AnalyticsRequest analyticsRequest = new AnalyticsRequest();
        analyticsRequest.setStartDate(new DateTime().minusMonths(1));
        return analyticService.getForSoldProducts(analyticsRequest.getStartDate(), analyticsRequest.getEndDate());
    }
}
