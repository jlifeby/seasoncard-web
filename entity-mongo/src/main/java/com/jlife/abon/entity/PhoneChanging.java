package com.jlife.abon.entity;

/**
 * @author Dzmitry Misiuk
 */
public class PhoneChanging extends Entity {

    private String newPhone;
    private String oldPhone;

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }
}

