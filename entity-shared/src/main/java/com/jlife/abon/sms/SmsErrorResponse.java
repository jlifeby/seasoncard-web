package com.jlife.abon.sms;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dzmitry Misiuk
 */
public class SmsErrorResponse implements Serializable {
    public SmsErrorResponse(String error) {
        this.error = error;
    }

    private String error;
    private UUID uuid;
    private Map<String, String> customFields;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }
}
