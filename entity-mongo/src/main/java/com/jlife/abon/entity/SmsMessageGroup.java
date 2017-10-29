package com.jlife.abon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "sms_groups")
public class SmsMessageGroup extends Entity {
    private String text;
    private List<String> smsIds;

    public SmsMessageGroup() {
        this.smsIds = new ArrayList<>();
    }

    public SmsMessageGroup(String text, String companyId) {
        this.text = text;
        this.companyId = companyId;
        this.smsIds = new ArrayList<>();
    }

    public SmsMessageGroup(String text, String companyId, List<String> smsIds) {
        this.text = text;
        this.companyId = companyId;
        this.smsIds = smsIds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getSmsIds() {
        return smsIds;
    }

    public void setSmsIds(List<String> smsIds) {
        this.smsIds = smsIds;
    }
}
