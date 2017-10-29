package com.jlife.abon.service.sms;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class UnionSmsService extends AbstractSmsService implements SmsService {

    private RocketSmsService rocketSmsService;
    private SmsAssistentSmsService smsAssistentSmsService;

    @Override
    public void sendSmsMessage(String phone, String text, String userId, SmsType smsType, boolean isPriority, boolean isHidden, String fromCompanyId) {
        if (isBelarusianPhone(phone)) {
            rocketSmsService.sendSmsMessage(phone, text, userId, smsType, isPriority, isHidden, fromCompanyId);
        } else {
            smsAssistentSmsService.sendSmsMessage(phone, text, userId, smsType, isPriority, isHidden, fromCompanyId);
        }
    }

    @Override
    protected void sendSmsAsync(String phone, String text, boolean isPriority, SmsMessage storedSmsMessage) {
        if (isBelarusianPhone(phone)) {
            rocketSmsService.sendSmsAsync(phone,text,isPriority, storedSmsMessage);
        } else {
            smsAssistentSmsService.sendSmsAsync(phone,text,isPriority, storedSmsMessage);
        }
    }

    @Override
    protected void sendSmsAsync(List<String> phonesTo, String text, boolean isPriority, Map<String, SmsMessage> storedSmsMessages) {
        List<String> belarusianPhones = new ArrayList<>();
        Map<String, SmsMessage> belarusianSmsMessages = new HashMap<>();
        List<String> otherPhones = new ArrayList<>();
        Map<String, SmsMessage> otherSmsMessages = new HashMap<>();
        for (String phone: phonesTo) {
            Map<String, SmsMessage> messagesMapRef;
            SmsMessage smsMessage = storedSmsMessages.get(phone);
            if (isBelarusianPhone(phone)) {
                belarusianPhones.add(phone);
                messagesMapRef = belarusianSmsMessages;
            } else {
                otherPhones.add(phone);
                messagesMapRef = otherSmsMessages;
            }
            if (smsMessage != null) {
                messagesMapRef.put(phone, smsMessage);
            }
        }
        if (!belarusianPhones.isEmpty()) {
            rocketSmsService.sendSmsAsync(belarusianPhones, text, isPriority, belarusianSmsMessages);
        }
        if (!otherPhones.isEmpty()) {
            smsAssistentSmsService.sendSmsAsync(otherPhones,text,isPriority, otherSmsMessages);
        }
    }

    public void setRocketSmsService(RocketSmsService rocketSmsService) {
        this.rocketSmsService = rocketSmsService;
    }

    public void setSmsAssistentSmsService(SmsAssistentSmsService smsAssistentSmsService) {
        this.smsAssistentSmsService = smsAssistentSmsService;
    }

    private boolean isBelarusianPhone(String phone) {
        return phone.startsWith("375");
    }
}
