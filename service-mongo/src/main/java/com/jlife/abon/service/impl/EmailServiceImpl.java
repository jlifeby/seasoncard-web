package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.Event;
import com.jlife.abon.entity.Participant;
import com.jlife.abon.entity.User;
import com.jlife.abon.service.EmailService;
import com.jlife.abon.service.TemplateService;
import com.jlife.mailsender.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class EmailServiceImpl implements EmailService {

    public static final String RECOVER_TEMPLATE = "recover";
    public static final String REGISTER_TO_EVENT_TEMPLATE = "register_to_event";
    public static final String FEEDBACK_TO_ORGANIZATION = "feedback_to_organization";
    public static final String FEEDBACK = "feedback";
    public static final String CONFIRM_TEMPLATE = "confirm";
    public static final String COMPANY_ADMIN_CREDENTIALS_TEMPLATE = "company_admin_credentials";
    public static final String REMIND_EVENT = "remind_event";
    public static final String REMOVING_EVENT = "removing_event";
    public static final String USER_MESSAGE_FROM_COMPANY = "user_message_from_company";
    public static final String ENROLL_NOTIFICATION_TEMPLATE = "enroll_notification";
    public static final String CLIENT_SELF_REGISTER_NOTIFICATION_TEMPLATE = "client_self_register_notification";
    public static final String ENROLL_NOTIFICATION_SUBJECT = "Оповещение о продаже абонемента";
    public static final String CLIENT_SELF_REGISTER_NOTIFICATION_SUBJECT = "Оповещение о регистрации нового клиента";

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    private MailSender mailSender;

    @Value("${app.url}")
    private String appUrl;

    @Value("${image.path}")
    private String imagePath;

    @Value("${site}")
    private String site;

    @Value("${noreply.email}")
    private String noReplyEmail;

    @Value("${support.email}")
    private String supportEmail;

    @Autowired
    private TemplateService templateService;

    @Override
    public void sentConfirm(User user) {
        String confirmationUrl = String.format("%s/confirmation/%s", appUrl, user.getConfirm());
        Map<String, Object> map = Collections.emptyMap();
        map.put("confirmation_link", confirmationUrl);
        map.put("user_name", user.getName());
        String text = renderTemplate(CONFIRM_TEMPLATE, map);
        String subject = "Подтверждение регистрации";
        mailSender.send(user.getEmail(), subject, text, true);
        LOG.info("Send confirmation link: " + confirmationUrl);
    }

    @Override
    public void sendRecoveringPassword(User user, String recoveringId) {
        String subject = "Восстановление пароля на " + site;
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("relativeRecoveringLink", "/recover-password?recoveringId=" + recoveringId);
        String content = renderTemplate(RECOVER_TEMPLATE, map);
        mailSender.sendContentWithFromAsync(noReplyEmail, user.getEmail(), subject, content, true);
        LOG.info(String.format("Send recover password for %s", user.getEmail()));
    }

    @Override
    public void sendEventRegistrationInfo(Participant participant, Event event) {
        String subject = "Вы зарегистрированы на событие " + event.getTitle();
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        String text = renderTemplate(REGISTER_TO_EVENT_TEMPLATE, map);
        String participantEmail = participant.getEmail();
        mailSender.send(participantEmail, subject, text, true);
        LOG.info(String.format("Send event registration to participant %s", participantEmail));
    }

    @Override
    public void sendFeedbackToOrganization(Company company, String name, String email, String message) {
        String subject = "Ваша компания получила письмо от " + name;
        Map<String, Object> map = new HashMap<>();
        map.put("organization", company);
        map.put("userEmail", email);
        map.put("userMessage", message);
        map.put("userName", name);
        String text = renderTemplate(FEEDBACK_TO_ORGANIZATION, map);
        //TODO send feedback
//        mailSender.send(organization.getEmail(), subject, text, true);
        LOG.info(String.format("Send feedback to organization %s from user %s ", company.getName(), email));
    }

    @Override
    public void sendFeedback(String name, String email, String subject, String message) {
        subject = "[Feedback] " + subject;
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", name);
        map.put("user_email", email);
        map.put("user_message", message);
        String text = renderTemplate(FEEDBACK, map);
        mailSender.sendContentWithFrom(noReplyEmail, supportEmail, subject, text, true);
        LOG.info(String.format("Send feedback %s from user %s ", email, name));
    }

    @Override
    public void sendAnonymousFeedback(String subject, String message) {
        sendFeedback("Anonymous", "unknown@seasoncard.by", subject, message);
    }

    @Override
    public void sendRemindForEvent(Event event, Participant participant) {
        String subject = "Напоминание о событии " + event.getTitle();
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        map.put("participant", participant);
        String text = renderTemplate(REMIND_EVENT, map);
        mailSender.send(participant.getEmail(), subject, text, true);
        LOG.info(String.format("Send reminder for event %s to user %s ", event.getId(), participant.getEmail()));
    }


    @Override
    public void sendRemovingInfo(Event event, Participant participant) {
        String subject = "Событие отменено " + event.getTitle();
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        map.put("participant", participant);
        String text = renderTemplate(REMOVING_EVENT, map);
        mailSender.send(participant.getEmail(), subject, text, true);
        LOG.info(String.format("Send removing info for event %s to user %s ", event.getId(), participant.getEmail()));
    }

    @Override
    public void sendEmailChangingConfirmation(User user, String confirmationId, String newEmail) {
        String subject = "Подтверждение email";
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("newEmail", newEmail);
        map.put("relativeConfirmationLink", "/confirm-email-changing/" + confirmationId);
        String text = renderTemplate(CONFIRM_TEMPLATE, map);
        mailSender.sendContentWithFromAsync(noReplyEmail, newEmail, subject, text, true);
        LOG.info(String.format("Sent email to confirm email changing for userId %s to %s", user.getId(), newEmail));
    }

    @Override
    public void sendCompanyAdminCredentials(User user, Company company, String password) {
        String subject = "Вы зарегистрированы в системе SeasonCard!";
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("company", company);
        map.put("password", password);
        String text = renderTemplate(COMPANY_ADMIN_CREDENTIALS_TEMPLATE, map);
        mailSender.sendContentWithFromAsync(noReplyEmail, user.getEmail(), subject, text, true);
        LOG.info(String.format("Sent email with credentials for company %s for user %s", company.getName(), user.getEmail()));
    }

    @Override
    public void sendUserMessageFromCompany(User user, String email, Company company, String subject, String content, boolean isHtml) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("company", company);
        map.put("content", content);
        String text = renderTemplate(USER_MESSAGE_FROM_COMPANY, map);

        mailSender.sendContentWithFromAsync(noReplyEmail, email, subject, text, true);
        LOG.info(String.format("Sent direct email with from company %s for user %s", company.getName(), user.getEmail()));
    }

    @Override
    public void sendEnrollNotificationFromCompany(Client client, Abonnement abon, Company company) {
        Map<String, Object> map = new HashMap<>();
        map.put("client", client);
        map.put("abon", abon);
        map.put("company", company);
        String text = renderTemplate(ENROLL_NOTIFICATION_TEMPLATE, map);

        mailSender.sendContentWithFromAsync(noReplyEmail, company.getCompanySettings().getNotificationEmail(), ENROLL_NOTIFICATION_SUBJECT, text, true);
        LOG.info(String.format("Sent enroll email to company %s, cardUUID %s, abon name %s", company.getName(), client.getCardUUID(), abon.getProduct().getName()));
    }

    @Override
    public void sendSelfRegisterClient(Client client, Company company, String toEmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("client", client);
        map.put("company", company);
        String text = renderTemplate(CLIENT_SELF_REGISTER_NOTIFICATION_TEMPLATE, map);

        mailSender.sendContentWithFromAsync(getNoReplyEmail(), toEmail, CLIENT_SELF_REGISTER_NOTIFICATION_SUBJECT, text, true);
        LOG.info("Sent client self register email to company {}, cardUUID {}", company.getName(), client.getCardUUID());
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    private String renderTemplate(String name, Map<String, Object> map) {
        return templateService.createFromTemplate(name, map);
    }

    public String getNoReplyEmail() {
        return noReplyEmail;
    }
}
