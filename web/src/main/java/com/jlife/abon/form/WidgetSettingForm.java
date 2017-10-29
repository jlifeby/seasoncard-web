package com.jlife.abon.form;

import com.jlife.abon.dto.WidgetSettingData;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Dzmitry Khralovich
 */
public class WidgetSettingForm implements Serializable {

    private String widgetSettingId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private boolean showHeader;
    private String logo;
    @NotBlank
    private String primaryPalette;

    @NotBlank
    private String buttonText;
    private boolean showButton;
    @NotBlank
    private String buttonPosition;
    @NotBlank
    private String buttonColor;
    private boolean buttonAnimation;
    @NotBlank
    private String formPosition;

    private boolean newClientEmailNotification;
    @Email
    private String notificationEmail;

    public WidgetSettingForm() {
        this.title = "Онлайн запись";
        this.description = "После заполнения формы наш администратор перезвонит Вам и подберёт удобное для Вас время!";
        this.buttonText = "Онлайн запись";
        this.showButton = true;
        this.buttonPosition = "bottom right";
        this.buttonColor = "#03a9f4";
        this.buttonAnimation = true;
        this.formPosition = "right";
        this.primaryPalette = "navy-blue-skin";
    }

    public WidgetSettingForm(WidgetSettingData widgetSettingData) {
        this.widgetSettingId = widgetSettingData.getId();
        this.title = widgetSettingData.getTitle();
        this.description = widgetSettingData.getDescription();
        this.showHeader = widgetSettingData.isShowHeader();
        this.logo = widgetSettingData.getLogo();
        this.primaryPalette = widgetSettingData.getPrimaryPalette();
        this.buttonText = widgetSettingData.getButtonText();
        this.showButton = widgetSettingData.isShowButton();
        this.buttonPosition = widgetSettingData.getButtonPosition();
        this.buttonColor = widgetSettingData.getButtonColor();
        this.buttonAnimation = widgetSettingData.isButtonAnimation();
        this.formPosition = widgetSettingData.getFormPosition();
        this.newClientEmailNotification = widgetSettingData.isNewClientEmailNotification();
        this.notificationEmail = widgetSettingData.getNotificationEmail();
    }

    public WidgetSettingData toWidgetSetting() {
        WidgetSettingData widgetSettingData = new WidgetSettingData();
        widgetSettingData.setId(getWidgetSettingId());
        widgetSettingData.setTitle(getTitle());
        widgetSettingData.setDescription(getDescription());
        widgetSettingData.setShowHeader(isShowHeader());
        widgetSettingData.setLogo(getLogo());
        widgetSettingData.setPrimaryPalette(getPrimaryPalette());
        widgetSettingData.setButtonText(getButtonText());
        widgetSettingData.setShowButton(isShowButton());
        widgetSettingData.setButtonPosition(getButtonPosition());
        widgetSettingData.setButtonColor(getButtonColor());
        widgetSettingData.setButtonAnimation(isButtonAnimation());
        widgetSettingData.setFormPosition(getFormPosition());
        widgetSettingData.setNewClientEmailNotification(isNewClientEmailNotification());
        widgetSettingData.setNotificationEmail(getNotificationEmail());

        return widgetSettingData;
    }

    public String getWidgetSettingId() {
        return widgetSettingId;
    }

    public void setWidgetSettingId(String widgetSettingId) {
        this.widgetSettingId = widgetSettingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrimaryPalette() {
        return primaryPalette;
    }

    public void setPrimaryPalette(String primaryPalette) {
        this.primaryPalette = primaryPalette;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    public String getButtonPosition() {
        return buttonPosition;
    }

    public void setButtonPosition(String buttonPosition) {
        this.buttonPosition = buttonPosition;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    public boolean isButtonAnimation() {
        return buttonAnimation;
    }

    public void setButtonAnimation(boolean buttonAnimation) {
        this.buttonAnimation = buttonAnimation;
    }

    public String getFormPosition() {
        return formPosition;
    }

    public void setFormPosition(String formPosition) {
        this.formPosition = formPosition;
    }

    public boolean isNewClientEmailNotification() {
        return newClientEmailNotification;
    }

    public void setNewClientEmailNotification(boolean newClientEmailNotification) {
        this.newClientEmailNotification = newClientEmailNotification;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }
}
