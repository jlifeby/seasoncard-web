package com.jlife.abon.dto;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class CompanySettingsData implements Serializable {

    private String notificationEmail;
    private boolean abonSoldEmailNotification;


    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public boolean isAbonSoldEmailNotification() {
        return abonSoldEmailNotification;
    }

    public void setAbonSoldEmailNotification(boolean abonSoldEmailNotification) {
        this.abonSoldEmailNotification = abonSoldEmailNotification;
    }
}