package com.jlife.abon.dto;

import java.io.Serializable;

public class ReplacingCardData implements Serializable {

    private String userId;
    private Long newCardUUID;

    public String getUserId() {
        return userId;
    }

    public Long getNewCardUUID() {
        return newCardUUID;
    }

    public ReplacingCardData() {
        this.userId = null;
        this.newCardUUID = null;
    }

}
