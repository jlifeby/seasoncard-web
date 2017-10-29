package com.jlife.abon.security;

import com.jlife.abon.api.ApiResponse;

public class AcceptingResponse extends ApiResponse {

    private boolean accepted;

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}