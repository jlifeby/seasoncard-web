package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.PromotionType;
import com.jlife.abon.utility.DefaultValues;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;

/**
 * @author Dzmitry Misiuk
 */
public class PromotionData extends AbstractPromotionData {

    private static DecimalFormat currencyDF = new DecimalFormat("#,###,##0.00");
    private static DecimalFormat percentageDF = new DecimalFormat("#");

    private CompanyData company;
    private double promotionValue;
    private boolean published;
    private boolean termless;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;
    @Deprecated
    private String logoPath;
    private MediaData logoMedia;
    private String logoMediaId;

    public PromotionData() {
        this.published = false;
        this.termless = true;
        this.logoPath = DefaultValues.DEFAULT_PROMOTION_IMAGE_PATH;
    }

    public double getPromotionValue() {
        return promotionValue;
    }

    public void setPromotionValue(double promotionValue) {
        this.promotionValue = promotionValue;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isTermless() {
        return termless;
    }

    public void setTermless(boolean termless) {
        this.termless = termless;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public MediaData getLogoMedia() {
        return logoMedia;
    }

    public void setLogoMedia(MediaData logoMedia) {
        this.logoMedia = logoMedia;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }

    public String getPromotionValueAsString() {
        if (promotionType.equals(PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION)) {
            return percentageDF.format(promotionValue) + " %";
        } else {
            return currencyDF.format(promotionValue) + " руб.";
        }
    }

    public CompanyData getCompany() {
        return company;
    }

    public void setCompany(CompanyData company) {
        this.company = company;
    }
}
