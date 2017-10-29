package com.jlife.abon.enumeration;

import java.io.Serializable;

public enum UserStatus implements Serializable {

    PRE_REGISTRATION("PRE_REGISTRATION"),
    ACTIVE("ACTIVE"),
    DEACTIVATED("DEACTIVATED");

    private final String description;

    private UserStatus(String role){
        this.description = role;
    }

    public String getDescription(){
        return description;
    }

}
