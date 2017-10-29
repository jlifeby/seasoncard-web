package com.jlife.abon.facade;

import com.jlife.abon.dto.ClientData;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Facade to working with client birthday.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientBirthdayFacade {

    /**
     * Returns list of clients of company with birthday between today and before daysBeforeBirthday days.
     *
     * @param companyId
     * @param daysBeforeBirthday
     * @return
     */
    List<ClientData> findClientsWithNearestBirthday(String companyId, int daysBeforeBirthday);

    List<ClientData> findClientsWithinDates(String companyId, DateTime startDate, DateTime endDate);
}
