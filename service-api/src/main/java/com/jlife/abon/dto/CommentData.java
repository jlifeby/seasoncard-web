package com.jlife.abon.dto;

import org.joda.time.DateTime;

/**
 * @author Dzmitry Misiuk
 */
public class CommentData extends BaseData {

    private String content;

    public CommentData() {
    }

    public CommentData(String content) {
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
