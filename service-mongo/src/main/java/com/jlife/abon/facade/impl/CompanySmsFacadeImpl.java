package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.SmsMessageData;
import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.facade.CompanySmsFacade;
import com.jlife.abon.service.sms.CompanySmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanySmsFacadeImpl extends AbstractFacade implements CompanySmsFacade {

    @Autowired
    private CompanySmsService companySmsService;

    @Override
    public void sendSmsMessage(long cardUUID, String content, String companyId) {
        this.companySmsService.sendMessage(cardUUID, content, companyId);
    }


    @Override
    public Page<SmsMessageData> findMessagesForClient(Long cardUUID, String companyId, Pageable pageable) {
        Page<SmsMessage> allMessagesForClient = companySmsService.findMessagesForClient(cardUUID, companyId, pageable);
        Page<SmsMessageData> smsMessageDatas = allMessagesForClient.map(m -> dataMapper.toSmsMessageData(m));
        return smsMessageDatas;
    }

    @Override
    public Page<SmsMessageData> findSmsMessagesForCompany(String companyId, Pageable pageable) {
        Page<SmsMessage> smsPage = smsService.findSmsMessagesForCompany(companyId, pageable);
        return smsPage.map(s -> dataMapper.toSmsMessageData(s));
    }

    @Override
    public SmsMessageData getMessage(String messageId, String companyId) {
        SmsMessage message = this.companySmsService.getMessage(messageId, companyId);
        return dataMapper.toSmsMessageData(message);
    }

    @Override
    public List<SmsMessageData> getAllCompanyMessages(String companyId) {
        List<SmsMessage> messages = this.companySmsService.getAllCompanyMessages(companyId);
        return dataMapper.toSmsMessageDataList(messages);
    }

    @Override
    public void sendSmsMessage(List<Long> cardUUIDs, String content, String companyId) {
        for (Long cardUUID : cardUUIDs) {
            if (cardUUID != null) {
                companySmsService.sendMessage(cardUUID, content, companyId);
            }
        }
    }

}
