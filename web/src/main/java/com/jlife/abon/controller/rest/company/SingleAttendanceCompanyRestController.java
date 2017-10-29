package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.SingleAttendanceData;
import com.jlife.abon.facade.SingleAttendanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api/company/")
public class SingleAttendanceCompanyRestController extends BaseController {

    @Autowired
    private SingleAttendanceFacade singleAttendanceFacade;

    @PostMapping(value = "/single-attendances/mark-attended", consumes = "application/json")
    public SingleAttendanceData markAttended(@RequestBody SingleAttendanceData singleAttendance) {
        return singleAttendanceFacade.markSingleAttendance(getSessionCompanyId(), singleAttendance);
    }
}
