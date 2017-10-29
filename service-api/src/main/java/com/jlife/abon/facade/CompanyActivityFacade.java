package com.jlife.abon.facade;

import com.jlife.abon.dto.ClientActivityData;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyActivityFacade {

    List<ClientActivityData> getClientActivities(String companyId, DateTime startDate, DateTime endDate);
}
