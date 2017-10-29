package com.jlife.abon.service;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.Event;
import com.jlife.abon.entity.Participant;
import com.jlife.abon.entity.User;

/**
 * @author Dzmitry Misiuk
 */
public interface EmailService {

    void sentConfirm(User user);

    void sendRecoveringPassword(User user, String password);

    void sendEventRegistrationInfo(Participant participant, Event event);

    void sendFeedbackToOrganization(Company company, String name, String email, String message);

    void sendFeedback(String name, String email, String subject, String message);

    void sendAnonymousFeedback(String subject, String message);

    void sendRemindForEvent(Event event, Participant participant);

    void sendRemovingInfo(Event event, Participant participant);

    void sendEmailChangingConfirmation(User user, String confirmationId, String newEmail);

    void sendCompanyAdminCredentials(User user, Company company, String password);

    void sendUserMessageFromCompany(User user, String email, Company company, String subject, String content, boolean isHtml);

    void sendEnrollNotificationFromCompany(Client client, Abonnement abon, Company company);

    void sendSelfRegisterClient(Client client, Company company, String toEmail);
}
