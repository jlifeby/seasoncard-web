package com.jlife.abon.service;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;
import com.jlife.abon.entity.SingleAttendance;
import org.joda.time.DateTime;

import java.util.stream.Stream;

/**
 * Service to work with trainer statistic.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TrainerStatisticService {


    /**
     * Returns stream of single attendances for trainer in particular period.
     *
     * @param trainerId
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<SingleAttendance> findSingleAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns stream of purchased abonnements for trainer between.
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<Abonnement> findPurchasedAbonnementsForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns abonnements with at least one attendance for particular trainer.
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<Abonnement> findAbonnementsWithAttendancesForTrainer(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns stream of attendances for trainer in particular period included in abonnement
     *
     * @param trainerId
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<Attendance> findAttendancesForTrainerBetween(String  companyId, String trainerId, DateTime startDate, DateTime endDate);
}


