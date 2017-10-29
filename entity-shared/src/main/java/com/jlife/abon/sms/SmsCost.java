package com.jlife.abon.sms;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class SmsCost implements Serializable {

    private int credits;
    private int money;

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
