package com.jlife.abon.entity;

import org.joda.time.DateTime;

/**
 * @author Dzmitry Misiuk
 */
public class Comment extends Entity {

    private String content;

    public Comment() {
    }

    public Comment(String content) {
        this.setCreatedDate(new DateTime());
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
