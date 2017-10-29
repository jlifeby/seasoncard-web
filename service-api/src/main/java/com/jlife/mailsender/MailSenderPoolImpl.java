package com.jlife.mailsender;

import com.jlife.mailsender.entity.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @author Khralovich Dzmitry
 */
public class MailSenderPoolImpl extends MailSenderImpl implements MailSenderPool {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MailSender mailSender;

    private void sentWrapper(Object... params) {
        taskExecutor.execute(new MailSenderRunnable(mailSender, params));
    }

    @Override
    public void send(String to, String subject, String text) {
        sentWrapper(to, subject, text);
    }

    @Override
    public void send(String to, String from, String subject, String text) {
        sentWrapper(to, from, subject, text);
    }

    @Override
    public void send(String to, String from, String subject, String template, boolean html) {
        sentWrapper(to, from, subject, template, html);
    }

    @Override
    public void send(String to, String from, String subject, String text, String attachmentPath, String attachmentName) {
        sentWrapper(to, from, subject, text, attachmentPath, attachmentName);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        sentWrapper(mimeMessage);
    }

    @Override
    public void send(MimeMessage[] mimeMessages) throws MailException {
        sentWrapper(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        sentWrapper(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
        sentWrapper(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        sentWrapper(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
        sentWrapper(simpleMessages);
    }

    @Override
    public void send(MailMessage mailMessage, String template, Map<String, Object> variables) {
        sentWrapper(mailMessage, template, variables);
    }
}
