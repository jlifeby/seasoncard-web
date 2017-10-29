package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.PromotionData;
import com.jlife.abon.enumeration.PromotionType;
import com.jlife.abon.utility.DefaultValues;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class PromotionForm implements Serializable {

    private String id;
    private String companyId;

    @NotBlank
    private String name;
    private String description;
    private PromotionType promotionType;
    @NotNull
    private double promotionValue;
    private boolean active;
    private boolean published;
    private boolean termless;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;

    @NotBlank
    private String logoPath;
    private String logoMediaId;

    public PromotionForm() {
        this.logoPath = DefaultValues.DEFAULT_PROMOTION_IMAGE_PATH;
        this.promotionType = PromotionType.PERCENTAGE_DISCOUNT_PRODUCT_PROMOTION;
        this.active = true;
        this.published = true;
        this.termless = true;
    }

    public PromotionForm(PromotionData promotion) {
        setId(promotion.getId());
        setCompanyId(promotion.getCompanyId());
        setName(promotion.getName());
        setDescription(promotion.getDescription());
        setPromotionType(promotion.getPromotionType());
        setPromotionValue(promotion.getPromotionValue());
        setTermless(promotion.isTermless());
        setEndDate(promotion.getEndDate());
        setLogoPath(promotion.getLogoPath());
        setLogoMediaId(promotion.getLogoMediaId());
        setActive(promotion.isActive());
        setPublished(promotion.isPublished());
    }

    public PromotionData toPromotion() {
        PromotionData promotion = new PromotionData();
        promotion.setId(getId());
        promotion.setName(getName());
        promotion.setDescription(getDescription());
        promotion.setPromotionType(getPromotionType());
        promotion.setPromotionValue(getPromotionValue());
        promotion.setTermless(isTermless());

        if(getEndDate() != null) {
            DateTimeZone requestTimeZone = DateTimeZone.forID("Europe/Minsk");
            DateTime updatedNewEndDate = getEndDate().toDateTime(requestTimeZone)
                    .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
            promotion.setEndDate(updatedNewEndDate);
        }
        promotion.setLogoPath(getLogoPath());
        promotion.setLogoMediaId(getLogoMediaId());
        promotion.setActive(isActive());
        promotion.setPublished(isPublished());
        return promotion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public Double getPromotionValue() {
        return promotionValue;
    }

    public void setPromotionValue(Double promotionValue) {
        this.promotionValue = promotionValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public boolean checkIsNew() {
        return id == null;
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

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }
}
