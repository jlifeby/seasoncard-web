package com.jlife.abon.dto;

import com.jlife.abon.enumeration.SenderState;
import org.joda.time.DateTime;

/**
 * @author Dzmitry Misiuk
 */
public class SenderTaskData extends BaseData {

    private DateTime sendDate;

    private SenderState state;

    private String text;

    private String subject;

    public SenderTaskData(){
        this.state = SenderState.NOT_STARTED;
    }

    public String event;

    public DateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(DateTime sendDate) {
        this.sendDate = sendDate;
    }

    public SenderState getState() {
        return state;
    }

    public void setState(SenderState state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
