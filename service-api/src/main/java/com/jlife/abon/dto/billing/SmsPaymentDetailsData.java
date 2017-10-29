package com.jlife.abon.dto.billing;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class SmsPaymentDetailsData extends PaymentDetailsData {

    private String smsId;

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }
}
