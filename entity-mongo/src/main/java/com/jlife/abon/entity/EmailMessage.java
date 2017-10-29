package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "emailmessages")
public class EmailMessage extends Entity {
    private String subject;
    private String content;
    private boolean isHtml;
    private String email;
    private String userId;
    private String fromCompanyId;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }
}
