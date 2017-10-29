package com.jlife.mailsender.entity;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Khralovich Dzmitry
 */
public class MailMessage extends SimpleMailMessage {
    
    private boolean html;

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }
}
