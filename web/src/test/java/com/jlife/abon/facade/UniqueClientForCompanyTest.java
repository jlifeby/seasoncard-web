package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.AttendanceData;
import com.jlife.abon.dto.ProductData;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class UniqueClientForCompanyTest extends ServiceBaseTestCase {

    @Autowired
    CompanyStatisticFacade companyStatisticFacade;

    @Test
    public void testUniqueClientOnly() {
        long initialCount = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(0, initialCount);
        ProductData product = new ProductData();
        product.setCompanyId(DEFAULT_COMPANY_ID);

        AbonnementData abonnementBought1 = new AbonnementData();
        abonnementBought1.setPurchaseDate(new DateTime().minusMonths(1));
        abonnementBought1.setProduct(product);
        abonnementBought1.setCardId("1");
        abonnementBought1 = abonnementFacade.save(abonnementBought1);
        AbonnementData abonnementBought2 = new AbonnementData();
        abonnementBought2.setPurchaseDate(new DateTime().minusMonths(1));
        abonnementBought2.setProduct(product);
        abonnementBought2.setCardId("2");
        abonnementBought2 = abonnementFacade.save(abonnementBought2);

        long afterTwoBought = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(2, afterTwoBought);

        AttendanceData attendance = new AttendanceData();
        attendance.setVisitDate(new DateTime().minusMonths(1));
        abonnementBought1.getAttendances().add(attendance);
        abonnementFacade.save(abonnementBought1);

        long afterAttendOnTheSameAbon = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(2, afterAttendOnTheSameAbon);

        AbonnementData abonnementBought3 = new AbonnementData();
        abonnementBought3.setPurchaseDate(new DateTime().minusMonths(1).withDayOfMonth(3));
        abonnementBought3.setProduct(product);
        abonnementBought3.setCardId("1");
        abonnementFacade.save(abonnementBought3);

        long afterBouthForTheSameCard = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(2, afterBouthForTheSameCard);

        AbonnementData abonnementBought4 = new AbonnementData();
        abonnementBought4.setPurchaseDate(new DateTime().minusMonths(3).withDayOfMonth(3));
        abonnementBought4.setProduct(product);
        abonnementBought4.setCardId("3");
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long afterBought3monthsAgo = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(2, afterBought3monthsAgo);


        AttendanceData attendance1 = new AttendanceData();
        attendance1.setVisitDate(new DateTime().minusMonths(1).withDayOfMonth(5));
        abonnementBought4.getAttendances().add(attendance1);
        abonnementBought4 = abonnementFacade.save(abonnementBought4);

        long afterAttendOnOldAbon = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(3, afterAttendOnOldAbon);

        AttendanceData attendance2 = new AttendanceData();
        attendance2.setVisitDate(new DateTime().minusMonths(1).withDayOfMonth(5));
        abonnementBought4.getAttendances().add(attendance2);
        abonnementFacade.save(abonnementBought4);

        long afterTwoAttendsOnOldAbon = companyStatisticFacade.countUniqueClientsInPreviousMonth(DEFAULT_COMPANY_ID);
        assertEquals(3, afterTwoAttendsOnOldAbon);

    }

}
