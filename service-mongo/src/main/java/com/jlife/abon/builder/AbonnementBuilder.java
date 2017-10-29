package com.jlife.abon.builder;

import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.AbonnementType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * Builder to prepare abonnement to purchase.
 * <p>
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class AbonnementBuilder {

    private Abonnement abonnement;
    private Abonnement sourceAbon;
    private Product baseProduct;
    private Promotion promotion;
    private double calculatedPrice;
    private Card clientCard;
    private Trainer trainer;

    private AbonnementBuilder() {
        this.abonnement = new Abonnement();
    }

    public static AbonnementBuilder getInstance() {
        return new AbonnementBuilder();
    }

    public AbonnementBuilder withSourceAbonnement(Abonnement sourceAbonnement) {
        this.sourceAbon = sourceAbonnement;
        return this;
    }

    public AbonnementBuilder withProduct(Product baseProduct) {
        this.baseProduct = baseProduct;
        return this;
    }

    public AbonnementBuilder withPromotion(Promotion promotion) {
        this.promotion = promotion;
        return this;
    }

    public AbonnementBuilder withCalculatedPrice(double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
        return this;
    }

    public AbonnementBuilder withCard(Card clientCard) {
        this.clientCard = clientCard;
        return this;
    }

    public Abonnement build() {
        checkNotNull();
        processBasicFields();
        processPrice();
        processInitialCountForIncludedProduct();
        processCountForAbonnement();
        processDates();
        processComment();
        processTrainer();
        return abonnement;
    }

    private void processTrainer() {
        if (trainer != null) {
            abonnement.setTrainerId(trainer.getId());
        }
    }

    private void processCountForAbonnement() {
        abonnement.setType(baseProduct.getAbonnementType());
        if (abonnement.getType() == AbonnementType.BY_UNIT) {
            abonnement.setUnitName(baseProduct.getUnitName());
            abonnement.setInitialCountOfUnits(baseProduct.getCountOfUnits());
            abonnement.setAvailableCountOfUnits(baseProduct.getCountOfUnits());
        }
        abonnement.setAvailableCountOfAttendances(baseProduct.getCountOfAttendances());
        abonnement.setInitialCountOfAttendances(baseProduct.getCountOfAttendances());
    }

    private void processBasicFields() {
        String cardId = clientCard.getId();
        abonnement.setCardId(cardId);
        abonnement.setProductId(baseProduct.getId());
        abonnement.setProduct(baseProduct);
        baseProduct.setSelling(0);
        abonnement.setPurchaseDate(DateTime.now());
    }

    private void processComment() {
        abonnement.setComments(sourceAbon.getComments());
    }

    private void processDates() {
        DateTimeZone requestTimeZone = DateTimeZone.forID("Europe/Minsk");
        DateTime startDateUTZ = sourceAbon.getStartDate().toDateTime(requestTimeZone);

        DateTime updatedStartDate = startDateUTZ
                .withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(1);
        DateTime endDateUTZ = sourceAbon.getEndDate().toDateTime(requestTimeZone);

        DateTime updatedEndDate = endDateUTZ
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        abonnement.setStartDate(updatedStartDate);
        abonnement.setEndDate(updatedEndDate);
    }

    private void processInitialCountForIncludedProduct() {
        if (baseProduct.getAbonnementType() == AbonnementType.BY_ATTENDANCE && sourceAbon.getInitialCountOfAttendances() != 0) {
            baseProduct.setCountOfAttendances(sourceAbon.getInitialCountOfAttendances());
        }
        if (baseProduct.getAbonnementType() == AbonnementType.BY_UNIT && sourceAbon.getInitialCountOfUnits() != 0) {
            baseProduct.setCountOfUnits(sourceAbon.getInitialCountOfUnits());
        }
    }

    private void processPrice() {
        if (hasPromotion()) {
            applyPromotion();
        } else if (sourceAbon.getProductPrice() != null) {
            baseProduct.setPrice(sourceAbon.getProductPrice());
        }

    }

    private void applyPromotion() {
        abonnement.setAppliedPromotions(Arrays.asList(promotion));
        double appliedPromotionValue = baseProduct.getPrice() - calculatedPrice;
        appliedPromotionValue = (double) Math.round(appliedPromotionValue * 100d) / 100d;
        abonnement.setAppliedPromotionValue(appliedPromotionValue);
        baseProduct.setPrice(calculatedPrice);
    }

    private void checkNotNull() {
        Assert.notNull(baseProduct);
        Assert.notNull(sourceAbon);
        Assert.notNull(clientCard);
        if (hasPromotion()) {
            Assert.notNull(calculatedPrice);
        }
    }

    private boolean hasPromotion() {
        return promotion != null;
    }


    public AbonnementBuilder withTrainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }
}
