package com.easyadmin.security.security.service;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final boolean isAdmin;
    public JwtAuthenticationResponse(String token,Boolean isAdmin) {
        this.token = token;
        this.isAdmin=isAdmin;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
