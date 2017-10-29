package com.jlife.abon.security;

import com.jlife.abon.dto.BaseData;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class WhoIAmResponse extends BaseData {

    private List<String> roles;

    private String lastName;

    private boolean authenticated;

    private String name;

    String email;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
