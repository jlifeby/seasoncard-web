package com.jlife.abon.dto;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class ClientActivityData implements Serializable {

    @NotNull
    List<DateTime> attendanceDates = new ArrayList<>();
    @NotNull
    List<DateTime> purchaseDates = new ArrayList<>();
    private Long cardUUID;
    private ClientData client;

    @NotNull
    public Long getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(@NotNull Long cardUUID) {
        this.cardUUID = cardUUID;
    }

    @NotNull
    public ClientData getClient() {
        return client;
    }

    public void setClient(@NotNull ClientData client) {
        this.client = client;
    }

    public List<DateTime> getAttendanceDates() {
        return attendanceDates;
    }

    public void setAttendanceDates(List<DateTime> attendanceDates) {
        this.attendanceDates = attendanceDates;
    }

    public List<DateTime> getPurchaseDates() {
        return purchaseDates;
    }

    public void setPurchaseDates(List<DateTime> purchaseDates) {
        this.purchaseDates = purchaseDates;
    }
}
