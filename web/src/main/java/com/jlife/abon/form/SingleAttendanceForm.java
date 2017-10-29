package com.jlife.abon.form;

import com.jlife.abon.dto.SingleAttendanceData;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class SingleAttendanceForm implements Serializable {

    private Double price;
    private String trainerId;
    private String comment;

    public SingleAttendanceForm() {
    }

    public SingleAttendanceData toSingleAttendance() {
        SingleAttendanceData singleAttendance = new SingleAttendanceData();
        singleAttendance.setPrice(getPrice());
        singleAttendance.setTrainerId(StringUtils.isBlank(getTrainerId()) ? null : getTrainerId());
        singleAttendance.setComment(getComment());
        return singleAttendance;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

