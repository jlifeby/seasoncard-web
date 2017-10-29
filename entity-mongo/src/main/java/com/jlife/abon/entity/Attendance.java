package com.jlife.abon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Part of subscription.
 *
 * @author Dzmitry Misiuk
 */
@Document
public class Attendance extends Entity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime visitDate;
    private String unitName;
    private int markUnits;
    private String trainerId;
    @Transient
    private Trainer Trainer;
    @Transient
    private String abonnementId;
    private boolean skipped;

    public DateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(DateTime visitDate) {
        this.visitDate = visitDate;
    }


    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setMarkUnits(int markUnits) {
        this.markUnits = markUnits;
    }

    public int getMarkUnits() {
        return markUnits;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public com.jlife.abon.entity.Trainer getTrainer() {
        return Trainer;
    }

    public void setTrainer(com.jlife.abon.entity.Trainer trainer) {
        Trainer = trainer;
    }

    public void setAbonnementId(String abonnementId) {
        this.abonnementId = abonnementId;
    }

    public String getAbonnementId() {
        return abonnementId;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }
}
