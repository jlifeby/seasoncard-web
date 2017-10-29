package com.jlife.abon.service;

import com.jlife.abon.TransactionType;
import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.PromotionType;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.TariffRepository;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class TariffService extends AbstractService {

    @Autowired
    SCConfigurationService scConfigurationService;

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private BillingService billingService;

    @Deprecated
    public Company setCurrentTariff(String companyId, String tariffId, Double totalPrice, DateTime startDate, DateTime endDate, boolean trial, String comment, boolean free) {
        Company company = companyRepository.findOne(companyId);
        Tariff tariff = tariffRepository.findOne(tariffId);
        if (!tariff.isActive()) {
            throw new NotAllowedException(ApiErrorCode.TARIFF_IS_NOT_ACTIVE, tariffId);
        }
        Tariff lastTariff = company.getCurrentTariff();
        List<Tariff> previousTariffs = company.getPreviousTariffs();
        if (lastTariff != null) {
            lastTariff.setFinishedDate(new DateTime());
            previousTariffs.add(lastTariff);
        }
        tariff.setStartDate(startDate);
        tariff.setEndDate(endDate);
        tariff.setTotalPrice(totalPrice);
        tariff.setTrial(trial);
        tariff.setComment(comment);
        tariff.setFree(free);
        company.setCurrentTariff(tariff);
        return companyRepository.save(company);
    }

    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    public Company setFreeTariffForCompany(String companyId) {
        Company company = companyRepository.findOne(companyId);
        company.setCurrentTariff(prepareFreeTariff(companyId));
        return companyRepository.save(company);
    }

    public Tariff prepareFreeTariff(String companyId) {
        Company company = companyRepository.findOne(companyId);
        Tariff tariff = this.getFreeActiveTariff();
        tariff.setStartDate(new DateTime());
        DateTime endDate = DateTime.now().plusDays(scConfigurationService.getDurationForFreeTariffDays());
        tariff.setEndDate(endDate);
        tariff.setLocalTotalPrice(new Price(company.getCurrency(), BigDecimal.ZERO));
        return tariff;
    }

    public Tariff getFreeActiveTariff() {
        List<Tariff> freeTariffs = tariffRepository.findAllByFreeAndActive(true, true);
        if (freeTariffs.isEmpty()) {
            throw new ResourceNotFoundException(ApiErrorCode.FREE_ACTIVE_TARIFF_NOT_FOUND, "Free tariff not found");
        } else if (freeTariffs.size() == 1) {
            return freeTariffs.get(0);
        } else {
            throw new AbonRuntimeException(ApiErrorCode.THERE_MANY_FREE_TARIFFS, "There are many free tariff. Inconsistency data. Contact admin");
        }
    }

    public Company setCurrentTariff(Tariff tariff, String companyId) {
        Assert.notNull(tariff);
        Assert.notNull(companyId);
        Assert.notNull(tariff.getId());
        Assert.notNull(tariff.getStartDate());
        Assert.isTrue(!tariff.getStartDate().isAfterNow());
        Assert.notNull(tariff.getEndDate());
        Assert.notNull(tariff.getLocalizedMonthPrice());
        Assert.notNull(tariff.getLocalTotalPrice());
        Assert.notNull(tariff.getLocalTotalPrice().getValue());
        Assert.notNull(tariff.getLocalTotalPrice().getCurrency());

        Company company = companyRepository.findOne(companyId);
        Account account = billingService.getAccountOrCreate(companyId);
        Price localTotalPrice = tariff.getLocalTotalPrice();

        Tariff existingTariff = tariffRepository.findOne(tariff.getId());
        if (!existingTariff.isActive()) {
            throw new NotAllowedException(ApiErrorCode.TARIFF_IS_NOT_ACTIVE, tariff.getId());
        }

        doPaymentForTariff(account, localTotalPrice);

        Tariff lastTariff = company.getCurrentTariff();
        List<Tariff> previousTariffs = company.getPreviousTariffs();
        if (lastTariff != null) {
            lastTariff.setFinishedDate(new DateTime());
            previousTariffs.add(lastTariff);
        }
        existingTariff.setStartDate(tariff.getStartDate());
        existingTariff.setEndDate(tariff.getEndDate());
        existingTariff.setLocalTotalPrice(localTotalPrice);
        existingTariff.setLocalizedMonthPrice(tariff.getLocalizedMonthPrice());
        existingTariff.setComment(tariff.getComment());
        existingTariff.setAppliedPromotions(tariff.getAppliedPromotions());
        existingTariff.setAppliedPromotionValue(tariff.getAppliedPromotionValue());
        company.setCurrentTariff(existingTariff);
        return companyRepository.save(company);
    }

    private void doPaymentForTariff(Account account, Price localTotalPrice) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setValue(localTotalPrice.getValue());
        transaction.setCurrency(localTotalPrice.getCurrency());
        transaction.setType(TransactionType.PAYMENT);
        transaction.setDetails(new TariffPaymentDetails());
        billingService.doPayment(transaction);
    }

    public Tariff calculateTariff(int countOfMonth, String tariffId, String companyId) {
        Company company = companyRepository.findOne(companyId);
        Tariff existingTariff = tariffRepository.findOne(tariffId);
        if (existingTariff == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ENTITY_NOT_FOUND, "Tariff", tariffId);
        }
        if (company == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ENTITY_NOT_FOUND, "Company", companyId);
        }
        if (!existingTariff.isActive()) {
            throw new NotAllowedException(ApiErrorCode.TARIFF_IS_NOT_ACTIVE, tariffId);
        }
        DateTime now = DateTime.now();
        DateTime endDate = now.plusMonths(countOfMonth);

        Tariff lastTariff = company.getCurrentTariff();
        if (lastTariff != null && lastTariff.getEndDate() != null && lastTariff.getEndDate().isAfter(now)) {
            Duration extensionPeriod = new Duration(now, lastTariff.getEndDate());
            endDate = endDate.plus(extensionPeriod);
        }
        existingTariff.setStartDate(now);
        existingTariff.setEndDate(endDate);
        Price monthPrice = existingTariff.getMonthPrice(company.getCountry());
        if (monthPrice == null) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, "tariff " + tariffId + "is not configured Properly. " +
                    "Does not have price for " + company.getCountry());
        }

        BigDecimal totalPriceValue = monthPrice.getValue().multiply(new BigDecimal(countOfMonth));
        Price totalPrice = new Price(company.getCurrency(), totalPriceValue);
        existingTariff.setLocalTotalPrice(totalPrice);

        TariffPromotion tariffPromotion = calculatePromotion(monthPrice.getValue(), countOfMonth);
        if (tariffPromotion != null) {
            applyPromotion(existingTariff, tariffPromotion);
        }
        return existingTariff;
    }

    private void applyPromotion(Tariff tariff, TariffPromotion tariffPromotion) {
        tariff.setAppliedPromotions(Arrays.asList(tariffPromotion));
        Price localTotalPrice = tariff.getLocalTotalPrice();
        Assert.isTrue(tariffPromotion.getPromotionType() == PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION);
        BigDecimal appliedPromotionValue = localTotalPrice.getValue()
                .multiply(tariffPromotion.getPromotionValue())
                .multiply(new BigDecimal(0.01));
        appliedPromotionValue = appliedPromotionValue.setScale(2, RoundingMode.HALF_UP);

        tariff.setAppliedPromotionValue(appliedPromotionValue);
        localTotalPrice.setValue(localTotalPrice.getValue().subtract(appliedPromotionValue));
    }

    private TariffPromotion calculatePromotion(BigDecimal value, int countOfMonth) {
        TariffPromotion tariffPromotion = null;
        BigDecimal promotionValue = null;
        if (countOfMonth >= 12) {
            promotionValue = new BigDecimal(20);
        } else if (countOfMonth >= 6) {
            promotionValue = new BigDecimal(10);
        } else if (countOfMonth >= 3) {
            promotionValue = new BigDecimal(5);
        }
        if (promotionValue != null) {
            tariffPromotion = new TariffPromotion(PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION, promotionValue);
        }
        return tariffPromotion;
    }

    public List<Tariff> getActiveTariffs() {
        return tariffRepository.findAllByActive(true);
    }

    public List<Tariff> getActivePaidTariffs() {
        return tariffRepository.findAllByFreeAndActive(false, true);
    }

}
