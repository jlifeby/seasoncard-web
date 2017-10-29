package com.jlife.abon.dto;

import com.jlife.abon.enumeration.SmsStatus;
import com.jlife.abon.enumeration.SmsType;
import com.jlife.abon.sms.SmsErrorResponse;
import com.jlife.abon.sms.SmsSuccessResponse;
import org.joda.time.DateTime;

/**
 * @author Dzmitry Misiuk
 */
public class SmsMessageData extends BaseData {

    private String phone;
    private DateTime sentDate;
    private SmsStatus smsStatus;
    private DateTime deliveredDate;
    private String userId;
    private SmsType smsType;
    private String text;
    private SmsSuccessResponse successResponse;
    private SmsErrorResponse errorResponse;
    private String fromCompanyId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(DateTime sentDate) {
        this.sentDate = sentDate;
    }

    public SmsStatus getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(SmsStatus smsStatus) {
        this.smsStatus = smsStatus;
    }

    public DateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(DateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SmsSuccessResponse getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(SmsSuccessResponse successResponse) {
        this.successResponse = successResponse;
    }

    public SmsErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(SmsErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }
}
