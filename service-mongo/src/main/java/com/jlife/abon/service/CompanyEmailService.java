package com.jlife.abon.service;

import com.jlife.abon.entity.*;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.EmailMessageRepository;
import com.jlife.abon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyEmailService extends AbstractService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private EmailService emailService;

    public void sendMessage(Long cardUUID, String subject, String content, boolean isHtml, String companyId) {
        Client client = clientService.getClientWithCardUUID(cardUUID, companyId);
        User user = userRepository.findOne(client.getUserId());
        if (!user.isAllowedEmailReceiving()) {
            // todo
            throw new NotAllowedException(ApiErrorCode.GENERAL_CODE);
        }
        String email = user.getEmail();
        if (email == null) {
            // set not-confirmed email if the main not-setup
            email = user.getNewEmail();
        }
        if (email == null) {
            // todo
            throw new NotAllowedException(ApiErrorCode.GENERAL_CODE);
        }
        Company company = companyRepository.findOne(companyId);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFromCompanyId(companyId);
        emailMessage.setContent(content);
        emailMessage.setEmail(email);
        emailMessage.setHtml(isHtml);
        emailMessage.setSubject(subject);
        emailMessage.setUserId(user.getId());
        emailService.sendUserMessageFromCompany(user, email, company, subject, content, isHtml);
        emailMessageRepository.save(emailMessage);
    }

    public List<EmailMessage> getAllMessagesForClient(Long cardUUID, String companyId) {
        Client client = clientService.getClientWithCardUUID(cardUUID, companyId);
        return emailMessageRepository.findByUserIdAndFromCompanyId(client.getUserId(), companyId);
    }

    public EmailMessage getMessage(String messageId, String companyId) {
        EmailMessage emailMessage = getMessage(messageId);
        if (!companyId.equals(emailMessage.getFromCompanyId())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_MESSAGE, messageId);
        }
        return emailMessage;
    }

    public List<EmailMessage> getAllCompanyMessages(String companyId) {
        return emailMessageRepository.findByFromCompanyId(companyId);
    }

    private EmailMessage getMessage(String messageId) {
        EmailMessage emailMessage = emailMessageRepository.findOne(messageId);
        if (emailMessage == null) {
            throw new ResourceNotFoundException(ApiErrorCode.MESSAGE_NOT_FOUND, messageId);
        }
        return emailMessage;
    }

    @Async
    public void sendEnrollNotification(Long cardUUID, String companyId, String abonId) {
        Client client = clientService.getClientWithCardUUID(cardUUID, companyId);
        Abonnement abon = abonnementService.getAbonnement(abonId);
        Company company = companyRepository.findOne(companyId);
        emailService.sendEnrollNotificationFromCompany(client, abon, company);
    }
}
