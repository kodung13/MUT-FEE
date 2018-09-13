package com.mut.feeapi.security.service;

import com.mut.feeapi.security.api.AuthenticationTokenDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.springframework.core.env.Environment;

import java.util.Date;

/**
 * Component which provides operations for issuing JWT tokens.
 *
 * @author TanagornS
 */
@Dependent
class AuthenticationTokenIssuer {

	
	Environment env;
	public AuthenticationTokenIssuer(Environment envTemp) {

		env = envTemp;
	}
	
    @Inject
    private AuthenticationTokenSettings settings;

    /**
     * Issue a JWT token
     *
     * @param authenticationTokenDetails
     * @return
     */
    public String issueToken(AuthenticationTokenDetails authenticationTokenDetails) {

    	AuthenticationTokenSettings settings = new AuthenticationTokenSettings(env);
    	
        return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authenticationTokenDetails.getAuthorities())
                .claim(settings.getRefreshCountClaimName(), authenticationTokenDetails.getRefreshCount())
                .claim(settings.getRefreshLimitClaimName(), authenticationTokenDetails.getRefreshLimit())
                .signWith(SignatureAlgorithm.HS256, settings.getSecret())
                .compact();
    }
}