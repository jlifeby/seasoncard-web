package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.dto.SingleAttendanceData;
import org.joda.time.DateTime;

import java.util.stream.Stream;

/**
 * Facade to work with trainer statistic
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface TrainerStatisticFacade {

    /**
     * Returns stream of single attendances for trainer in particular period.
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<SingleAttendanceData> findSingleAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns stream of purchased abonnements for trainer between.
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<AbonnementData> findPurchasedAbonnementsForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns abonnements with at least one attendance for particular trainer.
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<AbonnementData> findAbonnementsWithAttendancesForTrainer(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    /**
     * Returns stream of attendances for trainer in particular period included in abonnement
     *
     * @param companyId
     * @param trainerId
     * @param startDate
     * @param endDate
     * @return
     */
    Stream<AttendanceData> findAttendancesForTrainerBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);
}
