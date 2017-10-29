package com.jlife.abon.sms;

import com.jlife.abon.enumeration.SmsStatus;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class SmsStatusResponse implements Serializable {

    private int id;

    private SmsStatus status;

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
}
