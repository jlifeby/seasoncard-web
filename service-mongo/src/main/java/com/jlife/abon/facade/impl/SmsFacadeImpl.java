package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.SmsMessageData;
import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.facade.SmsFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class SmsFacadeImpl extends AbstractFacade implements SmsFacade {

    @Override
    public List<SmsMessageData> getAllMessages() {
        List<SmsMessage> allMessages = smsService.getAllMessages();
        return dataMapper.toSmsMessageDataList(allMessages);
    }

    @Override
    public SmsMessageData getMessage(String id) {
        SmsMessage message = smsService.getMessage(id);
        return dataMapper.toSmsMessageData(message);
    }

    @Override
    public void sendMessage(String phone, String content) {
        smsService.sendSmsMessage(phone, content);
    }

}