package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

/**
 * Part of subscription.
 *
 * @author Dzmitry Misiuk
 */
public class AttendanceData extends BaseData {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime visitDate;
    private String unitName;
    private int markUnits;
    private String trainerId;
    private TrainerData Trainer;
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

    public TrainerData getTrainer() {
        return Trainer;
    }

    public void setTrainer(TrainerData trainer) {
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
