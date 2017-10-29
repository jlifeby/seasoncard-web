package com.jlife.abon.mail;

/**
 * @author Khralovich Dzmitry
 */
public class Mail {

    public String toEmail;
    public String toCcEmail;
    public String toBccEmail;
    public String fromEmail;
    public String subject;
    public String text;

    public Mail() {
    }

    public Mail(String toEmail, String subject, String text) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.text = text;
    }

    public Mail(String toEmail, String subject, String text, String fromEmail) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.text = text;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToCcEmail() {
        return toCcEmail;
    }

    public void setToCcEmail(String toCcEmail) {
        this.toCcEmail = toCcEmail;
    }

    public String getToBccEmail() {
        return toBccEmail;
    }

    public void setToBccEmail(String toBccEmail) {
        this.toBccEmail = toBccEmail;
    }

}
