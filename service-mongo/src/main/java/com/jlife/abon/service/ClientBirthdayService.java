package com.jlife.abon.service;

import com.jlife.abon.entity.Client;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Service to work with client birthdays.
 * <p>
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientBirthdayService {


    /**
     * Returns list of clients of company with birthday between today and before daysBeforeBirthday days.
     *
     * @param companyId
     * @param daysBeforeBirthday
     * @return
     */
    List<Client> findClientsWithNearestBirthday(String companyId, int daysBeforeBirthday);


    /**
     * Returns list of clients of company with birthday between startDate and endDate
     *
     *
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Client> findClientsWithBirthdayBetween(String companyId, DateTime startDate, DateTime endDate);
}
