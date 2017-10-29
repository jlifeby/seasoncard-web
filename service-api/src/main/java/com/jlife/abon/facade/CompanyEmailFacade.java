package com.jlife.abon.facade;

import com.jlife.abon.dto.EmailMessageData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanyEmailFacade {

    void sendMessage(Long cardUUID, String subject, String content, boolean isHtml, String companyId);

    void sendEnrollNotification(Long cardUUID, String companyId, String abonId);

    List<EmailMessageData> getAllMessagesForClient(Long cardUUID, String companyId);

    EmailMessageData getMessage(String messageId, String companyId);

    List<EmailMessageData> getAllCompanyMessages(String companyId);
}
