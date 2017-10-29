package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.dto.statistic.CompanyStatisticData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.facade.CompanyStatisticFacade;
import com.jlife.abon.repository.CardRepository;
import com.jlife.abon.service.StatisticService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyStatisticFacadeImpl extends AbstractFacade implements CompanyStatisticFacade {


    @Autowired
    private StatisticService statisticService;

    @Autowired
    // todo remove
    private CardRepository cardRepository;

    @Override
    public long countUniqueClientsInPreviousMonth(String companyId) {
        DateTime startingOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .minusMonths(1)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime endOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        return this.countUniqueClientsBetween(companyId, startingOfPreviousMonth, endOfPreviousMonth);
    }

    @Override
    public long countUniqueClientsBetween(String companyId, DateTime startDate, DateTime endDate) {
        return companyStatisticService.countUniqueClientsBetween(companyId, startDate, endDate);
    }

    @Override
    public CurrentStatisticData getCurrentStatistic(String companyId) {
        return statisticService.getCurrentStatistic(companyId);
    }

    @Override
    public List<CompanyStatisticData> getAllCompanyStatisticDatas() {
        List<Company> allCompanies = companyService.findAll();
        List<CompanyStatisticData> statisticDatas = dataMapper.toCompanyStatisticDataList(allCompanies);
        for (CompanyStatisticData companyStatisticData : statisticDatas) {
            String companyId = companyStatisticData.getId();
            int countOfProducts = productService.countAllProducts(companyId);
            int countOfClients = clientService.countAlliClients(companyId);
            companyStatisticData.setCountOfProducts(countOfProducts);
            companyStatisticData.setCountOfClients(countOfClients);
            companyStatisticData.setCountOfActiveClients(this.countUniqueClientsInPreviousMonth(companyId));
            // todo optimize code
            companyStatisticData.setCountOfPhysicalCards(cardRepository.countByDeliveredToCompanyAndNfcUUIDNotNull(companyId));
            companyStatisticData.setCountOfFreePhysicalCards(cardRepository.countByFreeAndDeliveredToCompanyAndNfcUUIDNotNull(true, companyId));
            companyStatisticData.setCountOfVirtualCards(cardRepository.countByDeliveredToCompanyAndNfcUUIDNull(companyId));
            companyStatisticData.setCountOfFreeVirtualCards(cardRepository.countByFreeAndDeliveredToCompanyAndNfcUUIDNull(true, companyId));
        }

        return statisticDatas;
    }
}
