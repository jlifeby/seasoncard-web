package com.jlife.abon.dto;

/**
 * @author Dzmitry Khralovich
 */
public class TrainerData extends AbstractUserData {

    private String description;
    private String htmlContent;

    private boolean published;

    public TrainerData() {
        this.published = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}

