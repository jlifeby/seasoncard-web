package com.jlife.mailsender;

import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.mailsender.entity.MailMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
public class MailSenderImpl extends JavaMailSenderImpl implements MailSender {

    private String emailPersonal = "SeasonCard Team";

    public void send(String to, String subject, String text) {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        send(mailMessage);
    }

    public void send(String to, String from, String subject, String text) {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        send(mailMessage);
    }

    public void send(String to, String from, String subject, String text, String attachmentPath, String attachmentName) {
        sendEmail(to, from, subject, text, attachmentPath, attachmentName);
    }

    @Override
    public void send(String to, String subject, String text, boolean html) {

        MimeMessage message = super.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        super.send(message);
    }

    @Override
    public void sendContentWithFrom(String from, String to, String subject, String content, boolean isHtml) {

        MimeMessage message = super.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setFrom(from, emailPersonal);
            helper.setSubject(subject);
            helper.setText(content, isHtml);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, e.getMessage());
        }
        super.send(message);
    }

    private void sendEmail(final String to, final String from, final String subject, final String text,
                           final String attachmentPath, final String attachmentName) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(to);
                message.setFrom(new InternetAddress(from, emailPersonal));
                message.setSubject(subject);
                message.setText(text);
                if (!StringUtils.isBlank(attachmentPath)) {
                    FileSystemResource file = new FileSystemResource(attachmentPath);
                    message.addAttachment(attachmentName, file);
                }
            }
        };
        this.send(preparator);
    }

    public void send(MailMessage mailMessage) throws MailException {
        super.send(mailMessage);
    }

    public void send(MailMessage[] mailMessages) throws MailException {
        super.send(mailMessages);
    }

    @Override
    public List<MailMessage> getSents() {
        throw new RuntimeException("not supported method");
    }

    @Override
    public void sendContentWithFromAsync(String noReplyEmail, String to, String subject, String content, boolean isHtml) {
        new Thread(() -> sendContentWithFrom(noReplyEmail, to, subject, content, isHtml)).start();
    }
}
