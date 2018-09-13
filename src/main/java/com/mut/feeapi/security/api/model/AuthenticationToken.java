package com.mut.feeapi.security.api.model;

/**
 * API model for the authentication token.
 *
 * @author TanagornS
 */
public class AuthenticationToken {

    private String token;

    public AuthenticationToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}