package com.jlife.abon.criteria;

/**
 * Created by dmitry on 12/14/16.
 */
public class FullTextCriteria implements SearchCriteria {
    private String text;

    public FullTextCriteria(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
