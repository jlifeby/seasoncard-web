package com.jlife.mailsender;

import com.jlife.mailsender.entity.MailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Khralovich Dzmitry
 */
public interface MailSenderPool extends MailSender{

    public void send(String to, String subject, String text);

    public void send(String to, String from, final String subject, String text);

    public void send(String to, String from, String subject, String template, boolean html);

    public void send(String to, String from, String subject, String text, String attachmentPath, String attachmentName);

    MimeMessage createMimeMessage();

    MimeMessage createMimeMessage(InputStream contentStream) throws MailException;

    void send(MailMessage mailMessage) throws MailException;

    void send(MailMessage[] mailMessages) throws MailException;

    void send(MimeMessage mimeMessage) throws MailException;

    void send(MimeMessage[] mimeMessages) throws MailException;

    void send(MimeMessagePreparator mimeMessagePreparator) throws MailException;

    void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException;

    void send(MailMessage mailMessage, String template, Map<String, Object> variables);
}
