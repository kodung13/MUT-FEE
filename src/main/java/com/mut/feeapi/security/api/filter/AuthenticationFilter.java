package com.mut.feeapi.security.api.filter;

import com.mut.feeapi.security.api.AuthenticatedUserDetails;
import com.mut.feeapi.security.api.AuthenticationTokenDetails;
import com.mut.feeapi.security.api.TokenBasedSecurityContext;
import com.mut.feeapi.security.service.AuthenticationTokenService;
import com.mut.feeapi.user.domain.User;
import com.mut.feeapi.user.service.UserService;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

/**
 * JWT authentication filter.
 *
 * @author TanagornS
 */
@Provider
//@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

//    @Inject
//    private UserService userService;

//    @Inject
//    private AuthenticationTokenService authenticationTokenService;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    Environment env;
    

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String authenticationToken = authorizationHeader.substring(7);
            handleTokenBasedAuthentication(authenticationToken, requestContext);
            return;
        }
        // Other authentication schemes (such as Basic) could be supported
    }

    private void handleTokenBasedAuthentication(String authenticationToken, ContainerRequestContext requestContext) {

    	UserService userService =  new UserService();
    	AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService(env);
    	AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(authenticationToken);
        User user = userService.findByUsernameOrEmail(authenticationTokenDetails.getUsername(),jdbcTemplate);
        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getUSERNAME());

        boolean isSecure = requestContext.getSecurityContext().isSecure();
        SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails, authenticationTokenDetails, isSecure);
        requestContext.setSecurityContext(securityContext);
    }
}