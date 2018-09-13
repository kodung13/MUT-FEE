package com.mut.feeapi.security.api.resource;

import com.mut.feeapi.security.api.AuthenticationTokenDetails;
import com.mut.feeapi.security.api.TokenBasedSecurityContext;
import com.mut.feeapi.security.api.model.AuthenticationToken;
import com.mut.feeapi.security.api.model.UserCredentials;
import com.mut.feeapi.security.service.AuthenticationTokenService;
import com.mut.feeapi.security.service.UsernamePasswordValidator;
import com.mut.feeapi.user.domain.User;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JAX-RS resource class that provides operations for authentication.
 *
 * @author TanagornS
 */
@RequestScoped
@Path("auth")
@ComponentScan(basePackages = { "com.mut.feeapi.*" })
@PropertySource("classpath:application.properties")
public class AuthenticationResource {

    @Context
    private SecurityContext securityContext;

    
    @Autowired
	JdbcTemplate jdbcTemplate;
    
    @Autowired
    Environment env;
    /**
     * Validate user credentials and issue a token for the user.
     *
     * @param credentials
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response authenticate(UserCredentials credentials) {
    	
    	UsernamePasswordValidator usernamePasswordValidator = new UsernamePasswordValidator(jdbcTemplate);
    	AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService(env);
    	User user = usernamePasswordValidator.validateCredentials(credentials.getUsername(), credentials.getPassword());
        String token = authenticationTokenService.issueToken(user.getUSERNAME());
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        return Response.ok(authenticationToken).build();
    }

    /**
     * Refresh the authentication token for the current user.
     *
     * @return
     */
    @POST
    @Path("refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refresh() {

    	AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService(env);
        AuthenticationTokenDetails tokenDetails =
                ((TokenBasedSecurityContext) securityContext).getAuthenticationTokenDetails();
        String token = authenticationTokenService.refreshToken(tokenDetails);

        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        return Response.ok(authenticationToken).build();
    }
}
