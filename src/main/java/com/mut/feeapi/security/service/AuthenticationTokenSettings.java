package com.mut.feeapi.security.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mut.feeapi.common.configuration.Configurable;

/**
 * Settings for signing and verifying JWT tokens.
 *
 * @author TanagornS
 */
@Dependent
@ComponentScan(basePackages = { "com.mut.feeapi.*" })
@PropertySource("classpath:application.properties")
class AuthenticationTokenSettings {


//    @Inject
//    @Configurable("authentication.jwt.secret")
    private String secret;

//    @Inject
//    @Configurable("authentication.jwt.clockSkew")
    private Long clockSkew;

//    @Inject
//    @Configurable("authentication.jwt.audience")
    private String audience;

//    @Inject
//    @Configurable("authentication.jwt.issuer")
    private String issuer;

//    @Inject
//    @Configurable("authentication.jwt.claimNames.authorities")
    private String authoritiesClaimName = "authorities";

//    @Inject
//    @Configurable("authentication.jwt.claimNames.refreshCount")
    private String refreshCountClaimName = "refreshCount";

//    @Inject
//    @Configurable("authentication.jwt.claimNames.refreshLimit")
    private String refreshLimitClaimName = "refreshLimit";

    
    Environment env;
	public AuthenticationTokenSettings(Environment envTemp) {

		env = envTemp;
		secret = env.getProperty("authentication.jwt.validFor");
		clockSkew = Long.parseLong(env.getProperty("authentication.jwt.clockSkew"));
		audience = env.getProperty("authentication.jwt.audience");
		issuer = env.getProperty("authentication.jwt.issuer");
		authoritiesClaimName = env.getProperty("authentication.jwt.claimNames.authorities");
		refreshCountClaimName = env.getProperty("authentication.jwt.validFor");
		refreshLimitClaimName = env.getProperty("authentication.jwt.claimNames.refreshCount");
	}
    
    public String getSecret() {
        return secret;
    }

    public Long getClockSkew() {
        return clockSkew;
    }

    public String getAudience() {
        return audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAuthoritiesClaimName() {
        return authoritiesClaimName;
    }

    public String getRefreshCountClaimName() {
        return refreshCountClaimName;
    }

    public String getRefreshLimitClaimName() {
        return refreshLimitClaimName;
    }
}
