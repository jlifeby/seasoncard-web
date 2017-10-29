package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.EmailMessageData;
import com.jlife.abon.entity.EmailMessage;
import com.jlife.abon.facade.CompanyEmailFacade;
import com.jlife.abon.service.CompanyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanyEmailFacadeImpl extends AbstractFacade implements CompanyEmailFacade {

    @Autowired
    private CompanyEmailService companyEmailService;

    @Override public void sendMessage(Long cardUUID, String subject, String content, boolean isHtml, String companyId) {
        companyEmailService.sendMessage(cardUUID, subject, content, isHtml, companyId);
    }

    @Override
    public void sendEnrollNotification(Long cardUUID, String companyId, String abonId) {
        companyEmailService.sendEnrollNotification(cardUUID, companyId, abonId);
    }

    @Override public List<EmailMessageData> getAllMessagesForClient(Long cardUUID, String companyId) {
        List<EmailMessage> messages = companyEmailService.getAllMessagesForClient(cardUUID, companyId);
        return dataMapper.toEmailMessageDataList(messages);
    }

    @Override public EmailMessageData getMessage(String messageId, String companyId) {
        EmailMessage message = companyEmailService.getMessage(messageId, companyId);
        return dataMapper.toEmailMessageData(message);
    }

    @Override public List<EmailMessageData> getAllCompanyMessages(String companyId) {
        List<EmailMessage> messages = companyEmailService.getAllCompanyMessages(companyId);
        return dataMapper.toEmailMessageDataList(messages);
    }

}
