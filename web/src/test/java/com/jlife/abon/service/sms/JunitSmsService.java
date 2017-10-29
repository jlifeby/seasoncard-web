package com.jlife.abon.service.sms;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class JunitSmsService extends AbstractSmsService {

    private Logger logger = LoggerFactory.getLogger(JunitSmsService.class);

    @Override
    protected void sendSmsAsync(String phone, String text, boolean isPriority, SmsMessage storedSmsMessage) {
        logger.info("Send message to " + storedSmsMessage.getPhone());
        storedSmsMessage.setSmsStatus(SmsStatus.DELIVERED);
        smsMessageRepository.save(storedSmsMessage);
    }

    @Override
    protected void sendSmsAsync(List<String> phonesTo, String text, boolean isPriority, Map<String, SmsMessage> storedSmsMessages) {
        for (String phone: phonesTo) {
            final SmsMessage storedSmsMessage = storedSmsMessages.get(phone);
            if (storedSmsMessage != null) {
                sendSmsAsync(phone, text, isPriority, storedSmsMessage);
            }
        }
    }
}
