package com.jlife.abon.service;

import com.jlife.abon.entity.Account;
import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.entity.SmsMessageGroup;
import com.jlife.abon.entity.Transaction;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface BillingService {

    int SMS_UNIT_SIZE = 70;

    Account getAccountOrCreate(String companyId);

    Transaction doReplenishment(Transaction replenishment);

    void checkMoneyToSendSms(String text, String fromCompanyId);

    Transaction doPayment(Transaction transaction);

    Transaction doPaymentBasedOnSms(SmsMessage smsMessage, String fromCompanyId);

    Transaction doPaymentBasedOnSmsGroup(SmsMessageGroup smsGroup, String fromCompanyId);
}
