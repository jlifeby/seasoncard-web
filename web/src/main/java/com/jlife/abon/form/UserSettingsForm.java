package com.jlife.abon.form;

import com.jlife.abon.dto.UserData;
import com.jlife.abon.dto.UserSettingsData;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class UserSettingsForm implements Serializable {

    private boolean allowedEmailReceiving;

    private boolean allowedSmsReceiving;

    public UserSettingsForm() {
        this.allowedEmailReceiving = true;
        this.allowedEmailReceiving = true;
    }

    public UserSettingsForm(UserData user) {
        this.allowedEmailReceiving = user.isAllowedEmailReceiving();
        this.allowedSmsReceiving = user.isAllowedSmsReceiving();
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

    public UserSettingsData toUserSettings() {
        UserSettingsData userSettings = new UserSettingsData();
        userSettings.setAllowedSmsReceiving(this.isAllowedSmsReceiving());
        userSettings.setAllowedEmailReceiving(this.allowedEmailReceiving);
        return userSettings;
    }
}
