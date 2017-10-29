package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.dto.SingleAttendanceData;
import com.jlife.abon.facade.TrainerStatisticFacade;
import com.jlife.abon.service.TrainerStatisticService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class TrainerStatisticFacadeImpl extends AbstractFacade implements TrainerStatisticFacade {

    @Autowired
    private TrainerStatisticService trainerStatisticService;

    @Override
    public Stream<SingleAttendanceData> findSingleAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return trainerStatisticService.findSingleAttendancesForTrainerBetween(companyId, trainerId, startDate, endDate)
                .map(sa -> dataMapper.toSingleAttendanceData(sa));
    }

    @Override
    public Stream<AbonnementData> findPurchasedAbonnementsForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return trainerStatisticService.findPurchasedAbonnementsForTrainerBetween(companyId, trainerId, startDate, endDate)
                .map(a -> dataMapper.toAbonnementData(a));
    }

    @Override
    public Stream<AbonnementData> findAbonnementsWithAttendancesForTrainer(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return trainerStatisticService.findAbonnementsWithAttendancesForTrainer(companyId, trainerId, startDate, endDate)
                .map(a -> dataMapper.toAbonnementData(a));
    }

    @Override
    public Stream<AttendanceData> findAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return trainerStatisticService.findAttendancesForTrainerBetween(companyId, trainerId, startDate, endDate)
                .map(a -> dataMapper.toDataAttendance(a));
    }
}
