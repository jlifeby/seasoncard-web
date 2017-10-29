package com.jlife.abon.service.sms;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsType;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface SmsService {
    void sendSmsMessage(String phone, String text, String userId, SmsType smsType, boolean isPriority, boolean isHidden, String fromCompanyId);

    default void sendReplacingCardMessage(String phone, String userId, Long newCardUUID, String companyId) {
        this.sendSmsMessage(phone, "Вам выполнена замена карты на " + newCardUUID, userId, SmsType.CARD_REPLACING, true, false, companyId);
        getLogger().info("Card replaced successfully for user id = {} with new card uuid = {}", userId, newCardUUID);
    }

    void sendInitialPasswordBySms(String phone, String userId, long cardUUID, String password);

    void sendRecoveringPasswordBySms(String phone, String userId, long cardUUID, String password);

    void sendSmsMessage(String phone, String content);

    void sendSmsMessageFromCompany(String phone, String userId, String content, String companyId);

    List<SmsMessage> getAllMessages();

    SmsMessage getMessage(String id);

    int countRecoveringRequestsForToday(String phone);

    Page<SmsMessage> findSmsMessagesForCompany(String companyId, Pageable pageable);

    Logger getLogger();
}
