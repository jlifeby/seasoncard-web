package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.enumeration.AbonnementType;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The main object what we will sale(our companies will sale)
 *
 * @author Dzmitry Misiuk
 */
@Document
public class Abonnement extends Entity {

    private String cardId;
    private String productId;
    private int availableCountOfAttendances;
    private int initialCountOfAttendances;
    private List<Attendance> attendances = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime purchaseDate;

    private Product product;
    @Transient
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime endDate;

    private AbonnementType type;
    private String unitName;
    private int availableCountOfUnits;
    private int initialCountOfUnits;

    private List<Comment> comments;

    private List<Promotion> appliedPromotions;

    private double appliedPromotionValue;

    private String trainerId;
    @Transient
    private Trainer trainer;
    @Transient
    private Double productPrice;
    @Transient
    private String promotionId;
    @Transient
    private Long cardUUID;

    public Abonnement() {
        this.type = AbonnementType.BY_ATTENDANCE;
        this.unitName = "Минуты";
        this.startDate = DateTime.now().minusDays(1);
        this.endDate = DateTime.now().plusMonths(1).plusDays(1);
        this.comments = new ArrayList<>();
        this.appliedPromotions = new ArrayList<>();
    }

    public int getAvailableCountOfAttendances() {
        return availableCountOfAttendances;
    }

    public void setAvailableCountOfAttendances(int availableCountOfAttendances) {
        this.availableCountOfAttendances = availableCountOfAttendances;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public boolean checkIsActual() {
        return checkIsActual(DateTime.now());
    }

    public boolean checkIsActual(DateTime customDate) {
        final boolean baseCondition = this.active && startDate.isBefore(customDate) && endDate.isAfter(customDate);
        switch (this.getType()) {
            case BY_ATTENDANCE:
                return baseCondition && this.getAvailableCountOfAttendances() > 0;
            case BY_UNIT:
                return baseCondition && this.getAvailableCountOfUnits() > 0;
            case BY_TIME:
                return baseCondition;
            default:
                return baseCondition;
        }
    }

    public Integer countDaysLeft() {
        return Days.daysBetween(DateTime.now(), endDate).getDays() + 1;
    }

    public boolean checkIsFinished() {
        final boolean baseCondition = !active || endDate.isBeforeNow();
        switch (this.getType()) {
            case BY_ATTENDANCE:
                return baseCondition || this.getAvailableCountOfAttendances() <= 0;
            case BY_UNIT:
                return baseCondition  || this.getAvailableCountOfUnits() <= 0;
            case BY_TIME:
                return baseCondition;
            default:
                return baseCondition;
        }
    }

    public boolean checkIsFuture() {
        return active && startDate.isAfterNow();
    }

    // todo in future remove invoking set product when we get abonnement.
    // this will be don't required to set product for product when we get it
    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getInitialCountOfAttendances() {
        return initialCountOfAttendances;
    }

    public void setInitialCountOfAttendances(int initialCountOfAttendances) {
        this.initialCountOfAttendances = initialCountOfAttendances;
    }

    public DateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(DateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public AbonnementType getType() {
        return type;
    }

    public void setType(AbonnementType type) {
        this.type = type;
    }

    public int getCountOfAttendances() {
        return getAttendances().size();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        if (StringUtils.hasLength(comment)) {
            // not thread-safe
            Comment commentEntity = new Comment(comment);
            commentEntity.setId(String.valueOf(this.comments.size()));
            this.comments.add(commentEntity);
        }
    }

    public List<Promotion> getAppliedPromotions() {
        return appliedPromotions;
    }

    public void setAppliedPromotions(List<Promotion> appliedPromotions) {
        this.appliedPromotions = appliedPromotions;
    }

    public double getAppliedPromotionValue() {
        return appliedPromotionValue;
    }

    public void setAppliedPromotionValue(double appliedPromotionValue) {
        this.appliedPromotionValue = appliedPromotionValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


    public int getAvailableCountOfUnits() {
        return availableCountOfUnits;
    }

    public void setAvailableCountOfUnits(int availableCountOfUnits) {
        this.availableCountOfUnits = availableCountOfUnits;
    }

    public int getInitialCountOfUnits() {
        return initialCountOfUnits;
    }

    public void setInitialCountOfUnits(int initialCountOfUnits) {
        this.initialCountOfUnits = initialCountOfUnits;
    }

    public String getUnitNameAsString() {
        if (type.equals(AbonnementType.BY_UNIT)) {
            return unitName + " (израсходовано)";
        } else {
            return "Посещено";
        }
    }

    public String getUnitValueAsString() {
        if (type.equals(AbonnementType.BY_UNIT)) {
            return getCountOfUnits() + " из " + initialCountOfUnits;
        } else if (type.equals(AbonnementType.BY_TIME)) {
            return getCountOfAttendances() + " из " + "неограниченно";
        } else {
            return getCountOfAttendances() + " из " + initialCountOfAttendances;
        }
    }

    public int getCountOfUnits() {
        return getInitialCountOfUnits() - getAvailableCountOfUnits();
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(Long cardUUID) {
        this.cardUUID = cardUUID;
    }
}
