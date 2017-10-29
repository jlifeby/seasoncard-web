package com.jlife.abon.form;

import com.jlife.abon.dto.CompanySettingsData;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class CompanySettingsForm implements Serializable {

    @Email
    private String notificationEmail;
    private boolean abonSoldEmailNotification;

    public CompanySettingsForm() {
    }

    public CompanySettingsForm(CompanySettingsData companySettingsData) {
        this.notificationEmail = companySettingsData.getNotificationEmail();
        this.abonSoldEmailNotification = companySettingsData.isAbonSoldEmailNotification();
    }

    public CompanySettingsData toCompanySettings() {
        CompanySettingsData companySettingsData = new CompanySettingsData();
        companySettingsData.setNotificationEmail(this.notificationEmail);
        companySettingsData.setAbonSoldEmailNotification(this.abonSoldEmailNotification);
        return companySettingsData;
    }

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
