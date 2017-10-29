package com.jlife.abon.dto;

import com.jlife.abon.dto.billing.TariffPromotionData;
import com.jlife.abon.enumeration.Country;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class TariffData extends BaseData {

    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime finishedDate;
    private boolean trial;
    @Deprecated
    private double monthPrice;
    private Map<Country, PriceData> localizedMonthPrice;
    private double totalPrice;
    private PriceData localTotalPrice;
    private String comment;
    private boolean free;

    private int maxClients;

    private List<TariffPromotionData> appliedPromotions;
    private BigDecimal appliedPromotionValue;

    public TariffData() {
        this.free = false;
        this.localizedMonthPrice = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public DateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(DateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Deprecated
    public boolean isTrial() {
        return trial;
    }

    @Deprecated
    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    @Deprecated
    public double getMonthPrice() {
        return monthPrice;
    }

    @Deprecated
    public void setMonthPrice(double monthPrice) {
        this.monthPrice = monthPrice;
    }

    @Deprecated
    public double getTotalPrice() {
        return totalPrice;
    }

    @Deprecated
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public Map<Country, PriceData> getLocalizedMonthPrice() {
        return localizedMonthPrice;
    }

    public void setLocalizedMonthPrice(Map<Country, PriceData> localizedMonthPrice) {
        this.localizedMonthPrice = localizedMonthPrice;
    }

    public PriceData getMonthPrice(Country country) {
        return localizedMonthPrice.get(country);
    }

    public PriceData getLocalTotalPrice() {
        return localTotalPrice;
    }

    public void setLocalTotalPrice(PriceData localTotalPrice) {
        this.localTotalPrice = localTotalPrice;
    }

    public List<TariffPromotionData> getAppliedPromotions() {
        return appliedPromotions;
    }

    public void setAppliedPromotions(List<TariffPromotionData> appliedPromotions) {
        this.appliedPromotions = appliedPromotions;
    }

    public BigDecimal getAppliedPromotionValue() {
        return appliedPromotionValue;
    }

    public void setAppliedPromotionValue(BigDecimal appliedPromotionValue) {
        this.appliedPromotionValue = appliedPromotionValue;
    }

    public boolean isDead() {
        return getEndDate().plusDays(1).isBeforeNow();
    }

    public boolean isEndingInLessThanThreeDays() {
        return !isDead() && Days.daysBetween(DateTime.now(), getEndDate()).getDays() < 3;
    }

    public boolean isEndingInLessThanFiveDays() {
        return !isDead() && Days.daysBetween(DateTime.now(), getEndDate()).getDays() < 5;
    }
}
