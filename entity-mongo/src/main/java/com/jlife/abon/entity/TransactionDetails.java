package com.jlife.abon.entity;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public abstract class TransactionDetails {

    String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}