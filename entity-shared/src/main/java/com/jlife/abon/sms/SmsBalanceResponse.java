package com.jlife.abon.sms;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class SmsBalanceResponse implements Serializable {

    private int credits;
    private int balance;

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
