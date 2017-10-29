package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;
import com.jlife.abon.entity.SingleAttendance;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.TrainerStatisticService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class TrainerStatisticServiceImpl extends AbstractService implements TrainerStatisticService {


    @Override
    public Stream<SingleAttendance> findSingleAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return singleAttendanceRepository
                .findAllSingleAttendancesForCompanyIdAndTrainerIdWithVisitDateBetween(companyId, trainerId, startDate, endDate);
    }

    @Override
    public Stream<Abonnement> findPurchasedAbonnementsForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return abonnementRepository.findAllAbonnementsForCompanyIdAndTrainerIdWithPurchaseDateBetween(companyId, trainerId, startDate, endDate);
    }

    @Override
    public Stream<Abonnement> findAbonnementsWithAttendancesForTrainer(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        return abonnementRepository.findAllAbonnementsForCompanyIdAndTrainerIdWithVisitDateBetween(companyId, trainerId, startDate, endDate);
    }

    @Override
    public Stream<Attendance> findAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate) {
        Stream<Abonnement> abonnementStream = abonnementRepository.findAllAbonnementsForCompanyIdAndTrainerIdWithVisitDateBetween(companyId, trainerId, startDate, endDate);
        Stream<Attendance> attendanceStream = abonnementStream
                .map(a -> a.getAttendances())
                .flatMap(attendances -> attendances.stream())
                .filter(attendance -> trainerId.equals(attendance.getTrainerId()))
                .filter(attendance -> startDate.isBefore(attendance.getVisitDate()) && endDate.isAfter(attendance.getVisitDate()));
        return attendanceStream;
    }
}
