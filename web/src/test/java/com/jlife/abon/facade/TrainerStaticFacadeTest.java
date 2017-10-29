package com.jlife.abon.facade;

import com.jlife.abon.dto.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class TrainerStaticFacadeTest extends ServiceBaseTestCase {

    public static final DateTimeZone MINSK_TIME_ZONE = DateTimeZone.forID("Europe/Minsk");

    @Autowired
    CompanyStatisticFacade companyStatisticFacade;

    @Autowired
    TrainerStatisticFacade trainerStatisticFacade;

    @Autowired
    SingleAttendanceFacade singleAttendanceFacade;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testPurchasedForTrainer() {
        DateTime startingOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .minusMonths(1)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime endOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();


        long initialCount = trainerStatisticFacade
                .findPurchasedAbonnementsForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(0, initialCount);
        ProductData product = new ProductData();
        product.setId(DEFAULT_PRODUCT_ID_BY_TIME);
        product.setCompanyId(DEFAULT_COMPANY_ID);


        AbonnementData abonnementBought1 = new AbonnementData();
        abonnementBought1.setPurchaseDate(new DateTime().minusMonths(1));
        abonnementBought1.setProduct(product);
        abonnementBought1.setCardId("1");
        abonnementBought1.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementBought1.setProductId(product.getId());
        abonnementBought1 = abonnementFacade.save(abonnementBought1);

        long afterOneBought = trainerStatisticFacade
                .findPurchasedAbonnementsForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(1, afterOneBought);

        AbonnementData abonnementBought2 = new AbonnementData();
        abonnementBought2.setPurchaseDate(new DateTime().minusMonths(1));
        abonnementBought2.setProduct(product);
        abonnementBought2.setCardId("2");
        abonnementBought2.setTrainerId(NOT_COMPANY_TRAINER_ID);
        abonnementBought2 = abonnementFacade.save(abonnementBought2);

        long afterOneBoughtToAnother = trainerStatisticFacade
                .findPurchasedAbonnementsForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(1, afterOneBoughtToAnother);


        long initalAttendances = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();

        assertEquals(0, initalAttendances);

        long coutAbonnementWithAttendedInitial = trainerStatisticFacade
                .findAbonnementsWithAttendancesForTrainer(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(0, coutAbonnementWithAttendedInitial);

        AttendanceData attendance = new AttendanceData();
        attendance.setTrainerId(DEFAULT_TRAINER_ID);
        attendance.setVisitDate(new DateTime().minusMonths(1));
        abonnementBought1.getAttendances().add(attendance);
        abonnementFacade.save(abonnementBought1);

        long coutAbonnementWithAttendedAfterOne = trainerStatisticFacade
                .findAbonnementsWithAttendancesForTrainer(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(1, coutAbonnementWithAttendedAfterOne);

        long countOfAttendancesAfterOneVisit = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();

        assertEquals(1, countOfAttendancesAfterOneVisit);


        AbonnementData abonnementBought3 = new AbonnementData();
        abonnementBought3.setPurchaseDate(new DateTime().minusMonths(1).withDayOfMonth(3));
        abonnementBought3.setProduct(product);
        abonnementBought3.setCardId("1");
        abonnementBought3.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementFacade.save(abonnementBought3);

        long afterBouthForTheSameCard = trainerStatisticFacade
                .findPurchasedAbonnementsForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(2, afterBouthForTheSameCard);

        AbonnementData abonnementBought4 = new AbonnementData();
        abonnementBought4.setPurchaseDate(new DateTime().minusMonths(3).withDayOfMonth(3));
        abonnementBought4.setProduct(product);
        abonnementBought4.setCardId("3");
        abonnementBought4.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long afterBought3monthsAgo = trainerStatisticFacade
                .findPurchasedAbonnementsForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(2, afterBought3monthsAgo);


        AttendanceData attendance1 = new AttendanceData();
        attendance1.setVisitDate(new DateTime().minusMonths(1).withDayOfMonth(5));
        attendance1.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementBought4.getAttendances().add(attendance1);
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long afterAttendOnOldAbon = trainerStatisticFacade
                .findAbonnementsWithAttendancesForTrainer(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(2, afterAttendOnOldAbon);

        long countOfAttendences = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(2, countOfAttendences);

        AttendanceData attendance2 = new AttendanceData();
        attendance2.setVisitDate(new DateTime().minusMonths(1).withDayOfMonth(5));
        attendance2.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementBought4.getAttendances().add(attendance2);
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long countOfAbonnements = trainerStatisticFacade
                .findAbonnementsWithAttendancesForTrainer(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(2, countOfAbonnements);

        long countOfAttendancesTwice = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(3, countOfAttendancesTwice);


        AttendanceData attendance3 = new AttendanceData();
        attendance3.setVisitDate(new DateTime().minusMonths(1).withDayOfMonth(5));
        abonnementBought4.getAttendances().add(attendance3);
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long countOfAttendancesTherThirdWithouTrainer = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(3, countOfAttendancesTherThirdWithouTrainer);


        AttendanceData attendance4 = new AttendanceData();
        attendance4.setVisitDate(new DateTime().withDayOfMonth(5));
        attendance4.setTrainerId(DEFAULT_TRAINER_ID);
        abonnementBought4.getAttendances().add(attendance4);
        abonnementFacade.save(abonnementBought4);

        long countOfAttendancesTherThirdWithTrainerButNotPreviousMonth = trainerStatisticFacade
                .findAttendancesForTrainerBetween(DEFAULT_COMPANY_ID, DEFAULT_TRAINER_ID, startingOfPreviousMonth, endOfPreviousMonth).count();
        assertEquals(3, countOfAttendancesTherThirdWithTrainerButNotPreviousMonth);
    }

    @Test
    public void testEmptyStatistic() {
        CurrentStatisticData currentStatistic = companyStatisticFacade.getCurrentStatistic(DEFAULT_COMPANY_ID);
        assertEquals(0, currentStatistic.getHourAbonnements());
        assertEquals(0, currentStatistic.getHourAttendances());
        assertEquals(0, currentStatistic.getHourSingleAttendances());
        assertEquals(0, currentStatistic.getHourAbonnementsSum(), 0.01d);
        assertEquals(0, currentStatistic.getHourSingleAttendancesSum(), 0.01d);

        assertEquals(0, currentStatistic.getTodayAbonnements());
        assertEquals(0, currentStatistic.getTodayAttendances());
        assertEquals(0, currentStatistic.getTodaySingleAttendances());
        assertEquals(0, currentStatistic.getTodayAbonnementsSum(), 0.01d);
        assertEquals(0, currentStatistic.getTodaySingleAttendancesSum(), 0.01d);
    }

    @Test
    public void testSingleAttendanceStatistic() {
        SingleAttendanceData sourceSingleAttendance = new SingleAttendanceData();
        sourceSingleAttendance.setPrice(20d);
        singleAttendanceFacade.markSingleAttendance(DEFAULT_COMPANY_ID,
                sourceSingleAttendance);

        CurrentStatisticData data = companyStatisticFacade.getCurrentStatistic(DEFAULT_COMPANY_ID);
        assertEquals(0, data.getHourAbonnements());
        assertEquals(0, data.getHourAttendances());
        assertEquals(1, data.getHourSingleAttendances());
        assertEquals(0, data.getHourAbonnementsSum(), 0.01d);
        assertEquals(20d, data.getHourSingleAttendancesSum(), 0.01d);

        assertEquals(0, data.getTodayAbonnements());
        assertEquals(0, data.getTodayAttendances());
        assertEquals(1, data.getTodaySingleAttendances());
        assertEquals(0, data.getTodayAbonnementsSum(), 0.01d);
        assertEquals(20d, data.getTodaySingleAttendancesSum(), 0.01d);
    }

}
