package com.jlife.abon.security;

import com.jlife.abon.api.ApiResponse;
import com.jlife.abon.enumeration.Role;

public class AuthResponse extends ApiResponse {

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}