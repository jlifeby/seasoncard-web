package com.jlife.abon.dto;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class UserSettingsData implements Serializable {

    private boolean allowedEmailReceiving;

    private boolean allowedSmsReceiving;

    public UserSettingsData() {
        this.allowedEmailReceiving = true;
        this.allowedEmailReceiving = true;
    }

    public boolean isAllowedEmailReceiving() {
        return allowedEmailReceiving;
    }

    public void setAllowedEmailReceiving(boolean allowedEmailReceiving) {
        this.allowedEmailReceiving = allowedEmailReceiving;
    }

    public boolean isAllowedSmsReceiving() {
        return allowedSmsReceiving;
    }

    public void setAllowedSmsReceiving(boolean allowedSmsReceiving) {
        this.allowedSmsReceiving = allowedSmsReceiving;
    }
}
