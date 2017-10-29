package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.PreferredStartingDate;

/**
 * @author Dzmitry Misiuk
 */
public class ProductData extends BaseData {

    private CompanyData company;
    private int countOfAttendances;
    private String name;
    private String description;
    private String htmlContent;
    private double price;
    @Deprecated
    private String logoPath;
    private MediaData logoMedia;
    private String logoMediaId;

    private boolean published;

    @JsonIgnore
    private long selling;

    private AbonnementType abonnementType;
    private PreferredStartingDate preferredStartingDate;
    private String unitName;
    private int countOfUnits;

    public ProductData() {
        this.preferredStartingDate = PreferredStartingDate.PURCHASE_DATE;
        this.abonnementType = AbonnementType.BY_ATTENDANCE;
        this.published = true;
        this.unitName = "Минуты";
        this.countOfUnits = 1;
    }

    public int getCountOfAttendances() {
        return countOfAttendances;
    }

    public void setCountOfAttendances(int countOfAttendances) {
        this.countOfAttendances = countOfAttendances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public CompanyData getCompany() {
        return company;
    }

    public void setCompany(CompanyData company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLogoMedia(MediaData logoMedia) {
        this.logoMedia = logoMedia;
    }

    public MediaData getLogoMedia() {
        return logoMedia;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public AbonnementType getAbonnementType() {
        return abonnementType;
    }

    public void setAbonnementType(AbonnementType abonnementType) {
        this.abonnementType = abonnementType;
    }

    public PreferredStartingDate getPreferredStartingDate() {
        return preferredStartingDate;
    }

    public void setPreferredStartingDate(PreferredStartingDate preferredStartingDate) {
        this.preferredStartingDate = preferredStartingDate;
    }

    public long getSelling() {
        return selling;
    }

    public void setSelling(long selling) {
        this.selling = selling;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getCountOfUnits() {
        return countOfUnits;
    }

    public void setCountOfUnits(int countOfUnits) {
        this.countOfUnits = countOfUnits;
    }

    public String getCountOfAttendancesAsString() {
        if (abonnementType.equals(AbonnementType.BY_UNIT)) {
            return unitName + ": " + countOfUnits;
        } else if (abonnementType.equals(AbonnementType.BY_TIME)) {
            return "Посещений: неограниченно";
        } else {
            return "Посещений: " + countOfAttendances;
        }
    }
}
