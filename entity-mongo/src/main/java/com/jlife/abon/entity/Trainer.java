package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Khralovich
 */
@Document(collection = "trainers")
public class Trainer extends AbstractUser {

    private String description;
    private String htmlContent;

    private boolean published;

    public Trainer() {
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

