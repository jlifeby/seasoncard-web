package com.jlife.abon.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class ParticipantData extends BaseData {

    private String email;
    private String name;
    private String lastName;

    private String event;

    private Map<String, Object> fields = new HashMap<String, Object>();

    public ParticipantData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
