package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;

/**
 * Facade to mark attended based on attendance model.
 *
 *
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface MarkingAttendedFacade {

    /**
     * Markes attended based on company id and attendance object
     *
     * @param companyId
     * @param attendance
     * @return
     */
    AbonnementData markAttended(String companyId, AttendanceData attendance);

}
