package com.jlife.abon.dto;

import com.jlife.abon.enumeration.RecipientType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dzmitry Khralovich
 * @author Dzmitry Misiuk
 */
public abstract class MailingListData extends BaseData {

    private RecipientType recipientType;

    private String subject;
    private String content;
    private boolean isHtml;
    private Set<String> emails;

    public MailingListData() {
        this.emails = new HashSet<>();
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

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

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }
}
