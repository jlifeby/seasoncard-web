package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;
import com.jlife.abon.entity.Trainer;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.service.AbonnementService;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.MarkingAttendedService;
import com.jlife.abon.service.TrainerService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class MarkingAttendedServiceImpl extends AbstractService implements MarkingAttendedService {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private AbonnementService abonnementService;


    @Override
    public Abonnement markAttendedAsCompany(String companyId, Attendance sourceAttendance) {
        String abonnementId = sourceAttendance.getAbonnementId();
        Assert.notNull(abonnementId, "abonnementId should not be null");
        Abonnement abonnement = abonnementService.getOne(abonnementId);
        if (!abonnement.getProduct().getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_ABONNEMENT, companyId, abonnementId);
        }
        if (!abonnement.checkIsActual()) {
            throw new NotAllowedException(ApiErrorCode.ABONNEMENT_IS_NOT_ACTIVE, abonnementId);
        }
        if (abonnement.getType() == AbonnementType.BY_ATTENDANCE) {
            if (abonnement.getAvailableCountOfAttendances() < 1) {
                throw new NotAllowedException(ApiErrorCode.NOT_ENOUGH_ATTENDANCES_ON_ABONNEMENT, abonnementId);
            }
        }
        if (abonnement.getType() == AbonnementType.BY_UNIT) {
            int markUnits = sourceAttendance.getMarkUnits();
            Assert.state(markUnits > 0, "markUnits should be greater than 0");
            if (abonnement.getAvailableCountOfUnits() < markUnits) {
                throw new NotAllowedException(ApiErrorCode.NOT_ENOUGH_UNITS_ON_ABONNEMENT, abonnementId);
            }
        }
        Attendance attendance = new Attendance();
        attendance.setId(String.valueOf(abonnement.getAttendances().size() + 1));
        DateTime now = DateTime.now();
        DateTime visitDate = now;
        if (sourceAttendance.getVisitDate() != null) {
            visitDate = sourceAttendance.getVisitDate();
        }
        if (!abonnement.checkIsActual(visitDate)) {
            throw new NotAllowedException(ApiErrorCode.VISIT_DATE_IS_OUTSIDE_OF_ACTUAL_PERIOD_AF_ABONNEMENT, abonnementId);
        }
        attendance.setVisitDate(visitDate);
        attendance.setCreatedDate(now);
        if (abonnement.getType() == AbonnementType.BY_ATTENDANCE) {
            abonnement.setAvailableCountOfAttendances(abonnement.getAvailableCountOfAttendances() - 1);
            if (abonnement.getAvailableCountOfAttendances() == 0) {
                abonnement.setActive(false);
            }
        }
        if (abonnement.getType() == AbonnementType.BY_UNIT) {
            int markUnits = sourceAttendance.getMarkUnits();
            Assert.state(markUnits > 0, "markUnits should be greater than 0");
            abonnement.setAvailableCountOfUnits(abonnement.getAvailableCountOfUnits() - markUnits);
            if (abonnement.getAvailableCountOfUnits() == 0) {
                abonnement.setActive(false);
            }
            attendance.setUnitName(abonnement.getUnitName());
            attendance.setMarkUnits(markUnits);
        }
        String trainerId = sourceAttendance.getTrainerId();
        if (trainerId != null) {
            Trainer trainer = trainerService.getActiveOneWithOwnerCompanyId(trainerId, companyId);
            attendance.setTrainerId(trainerId);
        }
        attendance.setSkipped(sourceAttendance.isSkipped());
        abonnement.getAttendances().add(attendance);

        abonnementRepository.save(abonnement);
        return abonnement;
    }


    @Override
    public Abonnement markAttendedAsCompany(String abonnementId, String companyId, int markUnits) {
        Attendance sourceAttendance = new Attendance();
        sourceAttendance.setAbonnementId(abonnementId);
        sourceAttendance.setMarkUnits(markUnits);
        return markAttendedAsCompany(companyId, sourceAttendance);

    }
}

