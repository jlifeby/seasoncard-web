package com.jlife.abon.enumeration;

import java.io.Serializable;

public enum Role implements Serializable {

    ROLE_USER("ROLE_USER"),
    ROLE_ORGANIZER("ROLE_ORGANIZER"),
    ROLE_COMPANY("ROLE_COMPANY"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String description;

    private Role(String role){
        this.description = role;
    }

    public String getDescription(){
        return description;
    }

}
