package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.AbonnementData;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class EnrollForm implements Serializable {

    @NotNull
    private Long cardUUID;
    @NotNull
    private String productId;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime startDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime endDate;
    private Integer attendances;
    private Integer units;
    private Double price;
    private String trainerId;
    private String promotionId;
    private String message;

    public EnrollForm() {
    }

    public AbonnementData toAbonnement() {
        AbonnementData abonnement = new AbonnementData();
        abonnement.setProductId(getProductId());
        abonnement.setStartDate(getStartDate());
        abonnement.setEndDate(getEndDate());
        if (getAttendances() != null) {
            abonnement.setInitialCountOfAttendances(getAttendances());
        }
        if (getUnits() != null) {
            abonnement.setInitialCountOfUnits(getUnits());
        }
        abonnement.setProductPrice(getPrice());
        abonnement.setTrainerId(StringUtils.isBlank(getTrainerId()) ? null : getTrainerId());
        abonnement.setPromotionId(StringUtils.isBlank(getPromotionId()) ? null : getPromotionId());
        if (StringUtils.isNotBlank(message)) {
            abonnement.addComment(message);
        }
        return abonnement;
    }

    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(Long cardUUID) {
        this.cardUUID = cardUUID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Integer getAttendances() {
        return attendances;
    }

    public void setAttendances(Integer attendances) {
        this.attendances = attendances;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

