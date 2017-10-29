package com.jlife.abon.dto.billing;

public class SmsGroupPaymentDetailsData extends PaymentDetailsData {
    private String smsGroupId;

    public String getSmsGroupId() {
        return smsGroupId;
    }

    public void setSmsGroupId(String smsGroupId) {
        this.smsGroupId = smsGroupId;
    }
}
