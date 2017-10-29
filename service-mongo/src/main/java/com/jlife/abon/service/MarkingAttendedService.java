package com.jlife.abon.service;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;

/**
 * Service to mark attended.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface MarkingAttendedService {

    /**
     * Marking attended based on abonnementId, companyId, markUnits.
     * Not clear, because some arguments can be null.
     *
     * @param abonnementId
     * @param companyId
     * @param markUnits
     * @return
     * @deprecated use {@link #markAttendedAsCompany(String, Attendance)}} instead.
     */
    @Deprecated()
    Abonnement markAttendedAsCompany(String abonnementId, String companyId, int markUnits);

    /**
     * Mark user attended based on companyId and attendance.
     *
     * @param companyId
     * @param attendance
     * @return
     */
    Abonnement markAttendedAsCompany(String companyId, Attendance attendance);
}
