package com.easyadmin.security.security.service;

import com.easyadmin.security.security.AuthorityName;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final AuthorityName authorityName;

    public JwtAuthenticationResponse(String token, AuthorityName authorityName) {
        this.token = token;
        this.authorityName = authorityName;
    }

    public String getToken() {
        return this.token;
    }

    public AuthorityName getAuthorityName() {
        return authorityName;
    }
}
