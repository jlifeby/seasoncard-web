package com.jlife.abon.dto;

/**
 * @author Dzmitry Khralovich
 */
public class WidgetSettingData extends BaseData {

    private String title;
    private String description;

    private boolean showHeader;
    private String logo;
    private String primaryPalette;

    private String buttonText;
    private boolean showButton;
    private String buttonPosition;
    private String buttonColor;
    private boolean buttonAnimation;
    private String formPosition;

    private boolean newClientEmailNotification;
    private String notificationEmail;


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
