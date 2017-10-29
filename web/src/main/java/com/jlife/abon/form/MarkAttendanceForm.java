package com.jlife.abon.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlife.abon.dto.AttendanceData;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class MarkAttendanceForm implements Serializable {

    @NotNull
    private String abonnementId;
    private String trainerId;
    private Integer markUnits;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private DateTime visitDate;
    private boolean skipped;

    public MarkAttendanceForm() {
    }

    public AttendanceData toAttendance() {
        AttendanceData attendance = new AttendanceData();
        attendance.setAbonnementId(abonnementId);
        if (getMarkUnits() != null) {
            attendance.setMarkUnits(getMarkUnits());
        }
        attendance.setTrainerId(StringUtils.isBlank(getTrainerId()) ? null : getTrainerId());
        DateTime now = DateTime.now();
        attendance.setVisitDate(new DateTime(getVisitDate().getYear(),
                getVisitDate().getMonthOfYear(),
                getVisitDate().getDayOfMonth(),
                now.getHourOfDay(),
                now.getMinuteOfHour(),
                now.getSecondOfMinute()));
        attendance.setSkipped(isSkipped());
        return attendance;
    }

    public String getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(String abonnementId) {
        this.abonnementId = abonnementId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getMarkUnits() {
        return markUnits;
    }

    public void setMarkUnits(Integer markUnits) {
        this.markUnits = markUnits;
    }

    public DateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(DateTime visitDate) {
        this.visitDate = visitDate;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }
}

