package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.PriceData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.enumeration.Country;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dzmitry Khralovich
 */
public class TariffForm implements Serializable {

    private String id;
    private String name;
    private int maxClients;
    private boolean free;
    private boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime startDate;


    private Map<String, Integer> localizedMonthPrice;

    public TariffForm() {
        this.active = true;
        this.localizedMonthPrice = new HashMap<>();
        for (Country country : Country.values()) {
            localizedMonthPrice.put(country.name(), 0);
        }
    }

    public TariffData toTariffData() {
        TariffData tariffData = new TariffData();
        tariffData.setId(getId());
        tariffData.setName(getName());
        tariffData.setMaxClients(getMaxClients());
        tariffData.setFree(isFree());
        tariffData.setActive(isActive());
        tariffData.setStartDate(getStartDate());
        tariffData.setEndDate(getEndDate());

        Map<Country, PriceData> localizedMonthPrice = new HashMap<>();

        for (String key : getLocalizedMonthPrice().keySet()) {
            Country country = Country.valueOf(key);
            BigDecimal value = new BigDecimal(getLocalizedMonthPrice().get(key));
            localizedMonthPrice.put(country, new PriceData(country.getCurrency(), value));
        }
        tariffData.setLocalizedMonthPrice(localizedMonthPrice);

        return tariffData;
    }

    public static TariffForm toTariffForm(TariffData tariffData) {

        TariffForm tariffForm = new TariffForm();

        tariffForm.setId(tariffData.getId());
        tariffForm.setName(tariffData.getName());
        tariffForm.setMaxClients(tariffData.getMaxClients());
        tariffForm.setFree(tariffData.isFree());
        tariffForm.setActive(tariffData.isActive());
        tariffForm.setStartDate(tariffData.getStartDate());
        tariffForm.setEndDate(tariffData.getEndDate());

        for (Country country : tariffData.getLocalizedMonthPrice().keySet()) {
            PriceData priceData = tariffData.getLocalizedMonthPrice().get(country);
            tariffForm.getLocalizedMonthPrice().put(country.name(), priceData.getValue().intValue());
        }

        return tariffForm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Map<String, Integer> getLocalizedMonthPrice() {
        return localizedMonthPrice;
    }

    public void setLocalizedMonthPrice(Map<String, Integer> localizedMonthPrice) {
        this.localizedMonthPrice = localizedMonthPrice;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
}
