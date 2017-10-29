package com.jlife.abon.entity;

import com.jlife.abon.enumeration.SenderState;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dzmitry Misiuk
 */
@Document(collection = "senderTasks")
public class SenderTask extends Entity {

    private DateTime sendDate;

    private SenderState state;

    private String text;

    private String subject;

    public SenderTask(){
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
