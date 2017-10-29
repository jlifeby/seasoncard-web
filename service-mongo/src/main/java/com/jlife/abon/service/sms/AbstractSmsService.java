package com.jlife.abon.service.sms;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.entity.SmsMessageGroup;
import com.jlife.abon.enumeration.SmsStatus;
import com.jlife.abon.enumeration.SmsType;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.SmsMessageGroupRepository;
import com.jlife.abon.repository.SmsMessageRepository;
import com.jlife.abon.service.BillingService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
abstract public class AbstractSmsService implements SmsService {

    public static final String YOUR_CREDENTIALS_TEMPLATE = "Ваши данные для входа: карта - %s и пароль - %s";
    public static final String YOUR_RECOVERED_PASSWORD_TEMPLATE = "Восстановление доступа: карта - %s и пароль - %s";

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractSmsService.class);

    protected String senderName;

    protected String username;

    protected String password;

    @Autowired
    protected SmsMessageRepository smsMessageRepository;

    @Autowired
    protected SmsMessageGroupRepository smsMessageGroupRepository;

    @Autowired
    private BillingService billingService;

    @Override
    public void sendInitialPasswordBySms(String phone, String userId, long cardUUID, String password) {
        String text = String.format(YOUR_CREDENTIALS_TEMPLATE, String.valueOf(cardUUID), password);
        this.sendSmsMessage(phone, text, userId, SmsType.INITIAL_PASSWORD, true, true, null);
    }

    @Override
    public void sendRecoveringPasswordBySms(String phone, String userId, long cardUUID, String password) {
        String text = String.format(YOUR_RECOVERED_PASSWORD_TEMPLATE, String.valueOf(cardUUID), password);
        this.sendSmsMessage(phone, text, userId, SmsType.RECOVERING_PASSWORD, true, true, null);
    }

    @Override
    public void sendSmsMessage(String phone, String content) {
        this.sendSmsMessage(phone, content, null, SmsType.DIRECT_MESSAGE, true, false, null);
    }

    @Override
    public void sendSmsMessageFromCompany(String phone, String userId, String content, String companyId) {
        this.sendSmsMessage(phone, content, userId, SmsType.DIRECT_MESSAGE, true, false, companyId);
    }


    @Override
    public List<SmsMessage> getAllMessages() {
        return this.smsMessageRepository.findAll();
    }

    @Override
    public SmsMessage getMessage(String id) {
        SmsMessage smsMessage = smsMessageRepository.findOne(id);
        if (smsMessage == null) {
            throw new ResourceNotFoundException(ApiErrorCode.MESSAGE_NOT_FOUND, id);
        }
        return smsMessage;
    }

    @Override
    public int countRecoveringRequestsForToday(String phone) {
        DateTime endDate = DateTime.now();
        DateTime startDate = endDate.minusDays(1);
        return smsMessageRepository.countByPhoneAndSmsTypeAndSentDateBetween(phone, SmsType.RECOVERING_PASSWORD,
                startDate, endDate);
    }

    @Override
    public Page<SmsMessage> findSmsMessagesForCompany(String companyId, Pageable pageable) {
        return smsMessageRepository.findByFromCompanyId(companyId, pageable);
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // todo try to use rx
    public void sendSmsMessage(String phone, String text, String userId, SmsType smsType, boolean isPriority, boolean isHidden, String fromCompanyId) {
        boolean isDirectMessage = smsType.equals(SmsType.DIRECT_MESSAGE);
        if (isDirectMessage) {
            billingService.checkMoneyToSendSms(text, fromCompanyId);
        }

        final String smsGroupId = null;  // single message doesn't belong to any group
        final SmsMessage storedSmsMessage = prepareAndStoreSmsMessage(phone, text, userId, smsType, isHidden, fromCompanyId, smsGroupId);

        if (isDirectMessage) {
            doPaymentForSms(storedSmsMessage, fromCompanyId);
        }

        sendSmsAsync(phone, text, isPriority, storedSmsMessage);
    }

    public void sendSmsGroup(Map<String, String> userIdsToPhones, String text, SmsType smsType, boolean isPriority, boolean isHidden, String fromCompanyId) {
        boolean isDirectMessage = smsType.equals(SmsType.DIRECT_MESSAGE);
        if (isDirectMessage) {
            billingService.checkMoneyToSendSms(text, fromCompanyId);
        }

        final SmsMessageGroupAndSmsMap storedSmsGroupAndMessages = prepareAndStoreSmsGroup(userIdsToPhones, text, smsType, isHidden, fromCompanyId);
        if (isDirectMessage) {
            doPaymentForSmsGroup(storedSmsGroupAndMessages.getGroup(), fromCompanyId);
        }

        final List<String> phones = new ArrayList<>(userIdsToPhones.values());
        sendSmsAsync(phones, text, isPriority, storedSmsGroupAndMessages.getMessages());
    }

    private void doPaymentForSms(SmsMessage storedSmsMessage, String fromCompanyId) {
        billingService.doPaymentBasedOnSms(storedSmsMessage, fromCompanyId);
    }

    private void doPaymentForSmsGroup(SmsMessageGroup storedSmsGroup, String fromCompanyId) {
        billingService.doPaymentBasedOnSmsGroup(storedSmsGroup, fromCompanyId);
    }

    protected abstract void sendSmsAsync(String phone, String text, boolean isPriority, SmsMessage storedSmsMessage);

    protected abstract void sendSmsAsync(List<String> phonesTo, String text, boolean isPriority, Map<String, SmsMessage> storedSmsMessages);

    private String hideSmsTextIfNeeded(String text, boolean isHidden) {
        if (isHidden) {
            return "****";
        } else {
            return text;
        }
    }

    private SmsMessage prepareAndStoreSmsMessage(String phone, String text, String userId, SmsType smsType, boolean isHidden, String fromCompanyId, String smsGroupId) {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setCreatedDate(new DateTime());
        smsMessage.setPhone(phone);
        smsMessage.setSmsStatus(SmsStatus.SENDING);
        smsMessage.setSmsType(smsType);
        smsMessage.setUserId(userId);
        smsMessage.setFromCompanyId(fromCompanyId);
        // if sms group id is defined text is stored in sms group
        if (smsGroupId == null) {
            smsMessage.setText(hideSmsTextIfNeeded(text, isHidden));
        } else {
            smsMessage.setGroupId(smsGroupId);
        }
        return smsMessageRepository.save(smsMessage);
    }

    private SmsMessageGroupAndSmsMap prepareAndStoreSmsGroup(Map<String, String> userIdsToPhones, String text, SmsType smsType, boolean isHidden, String fromCompanyId) {
        final String transformedText = hideSmsTextIfNeeded(text, isHidden);
        SmsMessageGroup smsGroup = new SmsMessageGroup(transformedText, fromCompanyId);
        smsGroup = smsMessageGroupRepository.save(smsGroup);
        final Map<String, SmsMessage> storedSmsMap = new HashMap<>(userIdsToPhones.size());
        for (Map.Entry<String, String> userIdAndPhone : userIdsToPhones.entrySet()) {
            final String userId = userIdAndPhone.getKey();
            final String phone = userIdAndPhone.getValue();
            final String smsGroupId = smsGroup.getId();
            final SmsMessage storedSms = prepareAndStoreSmsMessage(phone, text, userId, smsType, isHidden, fromCompanyId, smsGroupId);
            storedSmsMap.put(phone, storedSms);
        }
        return new SmsMessageGroupAndSmsMap(smsGroup, storedSmsMap);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    class SmsMessageGroupAndSmsMap {
        private final SmsMessageGroup group;
        private final Map<String, SmsMessage> messages;

        SmsMessageGroupAndSmsMap(SmsMessageGroup group, Map<String, SmsMessage> messages) {
            this.group = group;
            this.messages = messages;
        }

        public SmsMessageGroup getGroup() {
            return group;
        }

        public Map<String, SmsMessage> getMessages() {
            return messages;
        }
    }
}
