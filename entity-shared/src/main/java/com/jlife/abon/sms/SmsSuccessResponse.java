package com.jlife.abon.sms;

import com.jlife.abon.enumeration.SmsStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Dzmitry Misiuk
 */
public class SmsSuccessResponse implements Serializable {

    private int id;
    private SmsStatus status;
    private SmsCost cost;
    private UUID uuid;
    private int smsCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SmsStatus getStatus() {
        return status;
    }

    public void setStatus(SmsStatus status) {
        this.status = status;
    }

    public SmsCost getCost() {
        return cost;
    }

    public void setCost(SmsCost cost) {
        this.cost = cost;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }
}
