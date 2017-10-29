package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.PromotionType;
import com.jlife.abon.utility.DefaultValues;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "promotion")
public class Promotion extends AbstractPromotion {

    private static DecimalFormat currencyDF = new DecimalFormat("#,###,##0.00");
    private static DecimalFormat percentageDF = new DecimalFormat("#");

    @Transient
    private Company company;
    private double promotionValue;
    private boolean published;
    private boolean termless;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;
    @Deprecated
    private String logoPath;
    private Media logoMedia;
    private String logoMediaId;

    public Promotion() {
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

    public Media getLogoMedia() {
        return logoMedia;
    }

    public void setLogoMedia(Media logoMedia) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
