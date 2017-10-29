package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.CompanyStatisticService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyStatisticServiceImpl extends AbstractService implements CompanyStatisticService {

    @Override
    public long countUniqueClientsBetween(String companyId, DateTime startDate, DateTime endDate) {
        Stream<Abonnement> abonStream = abonnementRepository
                .countUniqueAbonWithAtLeastOneAttendancesOrPurchasedByCompanyBetween(companyId, startDate, endDate);
        Stream<String> distinctedByCardId = abonStream.map(a -> a.getCardId()).distinct();
        return distinctedByCardId.count();
    }
}


