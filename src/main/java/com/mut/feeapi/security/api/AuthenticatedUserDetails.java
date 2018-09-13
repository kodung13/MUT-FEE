package com.mut.feeapi.security.api;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import com.mut.feeapi.security.domain.Authority;

/**
 * {@link Principal} implementation with a set of {@link Authority}.
 *
 * @author TanagornS
 */
public final class AuthenticatedUserDetails implements Principal {

    private  String username;
    private  Set<Authority> authorities;

    public AuthenticatedUserDetails(String username, Set<Authority> authorities) {
        this.username = username;
        this.authorities = Collections.unmodifiableSet(authorities);
    }
    
    public AuthenticatedUserDetails(String username) {
        this.username = username;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }
}