package com.jlife.abon.service.sms;

import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.SmsMessageRepository;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.ClientService;
import com.jlife.abon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CompanySmsService extends AbstractService {

    @Autowired
    @Qualifier(DEFAULT_SMS_SERVICE)
    private SmsService smsService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsMessageRepository smsMessageRepository;

    public void sendMessage(Long cardUUID, String content, String companyId) {
        Client client = clientService.getClientWithCardUUID(cardUUID, companyId);
        User user = userService.getUser(client.getUserId());
        if (!user.isAllowedSmsReceiving()) {
            LOG.warn("skipping user with id=" + user.getId() + " for sending sms because not allowed sms receiving");
            return;
        }
        smsService.sendSmsMessageFromCompany(client.getPhone(), client.getUserId(), content, companyId);
    }

    public Page<SmsMessage> findMessagesForClient(Long cardUUID, String companyId, Pageable pageable) {
        Client client = clientService.getClientWithCardUUID(cardUUID, companyId);
        String userId = client.getUserId();
        return smsMessageRepository.findByUserIdAndFromCompanyId(userId, companyId, pageable);
    }

    public SmsMessage getMessage(String messageId, String companyId) {
        SmsMessage smsMessage = smsMessageRepository.findOne(messageId);
        if (smsMessage == null) {
            throw new ResourceNotFoundException(ApiErrorCode.MESSAGE_NOT_FOUND, messageId);
        }
        if (!companyId.equals(smsMessage.getFromCompanyId())) {
            throw new ResourceNotFoundException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_MESSAGE, messageId);
        }
        return smsMessage;
    }

    public List<SmsMessage> getAllCompanyMessages(String companyId) {
        return smsMessageRepository.findByFromCompanyId(companyId);
    }
}
