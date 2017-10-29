package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

/**
 * EntityData to contain information about attendance not related to abonnement.
 *
 * @author Dzmitry Misiuk
 */
public class SingleAttendanceData extends AttendanceData {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime purchaseDate;
    private double price;
    private String comment;

    public SingleAttendanceData() {
        DateTime now = new DateTime();
        this.purchaseDate = now;
        this.setVisitDate(now);
    }

    public DateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(DateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
