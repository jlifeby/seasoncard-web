package com.jlife.abon.mail;

import com.jlife.mailsender.MailSender;
import com.jlife.mailsender.entity.MailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.List;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class JunitMailSender implements MailSender {

    Logger logger = LoggerFactory.getLogger(JunitMailSender.class);

    @Override
    public void send(String to, String subject, String text) {
        logger.info("sending email to " +to);
    }

    @Override
    public void send(String to, String from, String subject, String text) {
        send(to, subject, text);
    }

    @Override
    public void send(String to, String from, String subject, String text, String attachmentPath, String attachmentName) {
            send(to, subject, text);
    }

    @Override
    public void send(String to, String subject, String content, boolean html) {
        send(to, subject, content);
    }

    @Override
    public void sendContentWithFrom(String from, String to, String subject, String content, boolean isHtml) {
        send(to, subject, content);
    }

    @Override
    public List<MailMessage> getSents() {
        return null;
    }

    @Override
    public void sendContentWithFromAsync(String noReplyEmail, String to, String subject, String content, boolean isHtml) {
        send(to, subject, to);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {

    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {

    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
