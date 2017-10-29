package com.jlife.abon.criteria;

/**
 * Created by dmitry on 12/14/16.
 */
public class ExpiringAbonementCriteria implements SearchCriteria {
    private Integer daysLeft;
    private Integer attendancesLeft;
    private Float unitsLeftRatio;

    public ExpiringAbonementCriteria() {
        this(3, 1, 0.1f);
    }

    public ExpiringAbonementCriteria(Integer daysLeft, Integer attendancesLeft, Float unitsLeftRatio) {
        this.daysLeft = daysLeft;
        this.attendancesLeft = attendancesLeft;
        this.unitsLeftRatio = unitsLeftRatio;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Integer getAttendancesLeft() {
        return attendancesLeft;
    }

    public void setAttendancesLeft(Integer attendancesLeft) {
        this.attendancesLeft = attendancesLeft;
    }

    public Float getUnitsLeftRatio() {
        return unitsLeftRatio;
    }

    public void setUnitsLeftRatio(Float unitsLeftRatio) {
        this.unitsLeftRatio = unitsLeftRatio;
    }
}
