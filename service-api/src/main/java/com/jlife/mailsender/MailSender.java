package com.jlife.mailsender;

import com.jlife.mailsender.entity.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
public interface MailSender extends JavaMailSender {

    public void send(String to, String subject, String text);

    public void send(String to, String from, final String subject, String text);

    public void send(String to, String from, String subject, String text, String attachmentPath, String attachmentName);

    public void send(String to, String subject, String content, boolean html);

    public void sendContentWithFrom(String from, String to, String subject, String content, boolean isHtml);

    public List<MailMessage> getSents();

    void sendContentWithFromAsync(String noReplyEmail, String to, String subject, String content, boolean isHtml);
}
