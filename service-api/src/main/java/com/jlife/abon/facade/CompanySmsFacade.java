package com.jlife.abon.facade;

import com.jlife.abon.dto.SmsMessageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CompanySmsFacade {
    void sendSmsMessage(long cardUUID, String content, String companyId);

    Page<SmsMessageData> findMessagesForClient(Long cardUUID, String companyId, Pageable pageable);

    SmsMessageData getMessage(String messageId, String companyId);

    List<SmsMessageData> getAllCompanyMessages(String companyId);

    Page<SmsMessageData> findSmsMessagesForCompany(String companyId, Pageable pageable);

    /**
     * Sending mass sms messages to clients by their card UUIDs
     * @param cardUUIDs
     * @param content
     * @param companyId
     */
    void sendSmsMessage(List<Long> cardUUIDs, String content, String companyId);
}
