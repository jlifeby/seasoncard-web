package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.facade.MarkingAttendedFacade;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class MarkingAttendedFacadeImpl extends AbstractFacade implements MarkingAttendedFacade {

    @Override
    public AbonnementData markAttended(String companyId, AttendanceData attendance) {
        Abonnement updatedAbonnement = markingAttendedService.markAttendedAsCompany(companyId, dataMapper.fromAttendanceData(attendance));
        return dataMapper.toAbonnementData(updatedAbonnement);
    }
}
