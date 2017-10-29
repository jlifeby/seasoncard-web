package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.Tariff;
import com.jlife.abon.facade.TariffFacade;
import com.jlife.abon.repository.TariffRepository;
import com.jlife.abon.service.TariffService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class TariffFacadeImpl extends AbstractFacade implements TariffFacade {

    @Autowired
    protected TariffRepository tariffRepository;

    @Autowired
    protected TariffService tariffService;

    @Override
    public TariffData createTariff(TariffData tariff) {
        Tariff newTariff = tariffRepository.save(dataMapper.toTariff(tariff));
        return dataMapper.toTariffData(newTariff);
    }

    @Override
    public TariffData getTariff(String id) {
        Tariff tariff = tariffRepository.findOne(id);
        return dataMapper.toTariffData(tariff);
    }

    @Override
    public TariffData updateTariff(TariffData tariff) {
        // todo move to service
        Tariff existedTariff = tariffRepository.findOne(tariff.getId());
        existedTariff.setName(tariff.getName());
        existedTariff.setMonthPrice(tariff.getMonthPrice());
        existedTariff.setTrial(tariff.isTrial());
        existedTariff.setFree(tariff.isFree());
        existedTariff.setMaxClients(tariff.getMaxClients());
        existedTariff.setActive(tariff.isActive());
        existedTariff.setLocalizedMonthPrice(dataMapper.toPriceMap(tariff.getLocalizedMonthPrice()));
        Tariff updatedTariff = tariffRepository.save(existedTariff);
        return dataMapper.toTariffData(updatedTariff);
    }

    @Override
    public List<TariffData> getAllTariffs() {
        List<Tariff> availableTariffs = tariffService.getAllTariffs();
        return dataMapper.toTariffDataList(availableTariffs);
    }

    @Override
    public CompanyData setCurrentTariff(String companyId, String tariffId, Double totalPrice, DateTime startDate, DateTime endDate, boolean trial, String comment, boolean free) {
        Company company = tariffService.setCurrentTariff(companyId, tariffId, totalPrice, startDate, endDate, trial, comment, free);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public CompanyData setCurrentTariff(TariffData tariffData, String companyId) {
        Company company = tariffService.setCurrentTariff(dataMapper.toTariff(tariffData), companyId);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public TariffData calculateTariff(int countOfMonth, String tariffId, String companyId) {
        return  dataMapper.toTariffData(tariffService.calculateTariff(countOfMonth, tariffId, companyId));
    }

    @Override
    public void removeProduct(TariffData tariff) {
        // todo check existed company who use it
        tariffRepository.delete(dataMapper.toTariff(tariff));
    }

    @Override
    public List<TariffData> getActiveTariffs() {
        return dataMapper.toTariffDataList(tariffService.getActiveTariffs());
    }

    @Override
    public List<TariffData> getActivePaidTariffs() {
        return dataMapper.toTariffDataList(tariffService.getActivePaidTariffs());
    }
}
