package com.mut.feeapi.security.service;


import com.mut.feeapi.user.domain.User;
import com.mut.feeapi.user.service.UserService;
import com.mut.feeapi.security.exception.AuthenticationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Component for validating user credentials.
 *
 * @author TanagornS
 */
@ApplicationScoped
public class UsernamePasswordValidator {

	JdbcTemplate jdbcTemplate;
	public UsernamePasswordValidator(JdbcTemplate jdbcTemp) {

		jdbcTemplate = jdbcTemp;

	}
	
    /**
     * Validate username and password.
     *
     * @param username
     * @param password
     * @return
     */
    public User validateCredentials(String username, String password) {

    	UserService userService =  new UserService();
    	PasswordEncoder passwordEncoder =  new PasswordEncoder();
        User user = userService.findByUsernameOrEmail(username,jdbcTemplate);

        if (user == null) {
            // User cannot be found with the given username/email
            throw new AuthenticationException("Bad credentials.");
        }

        if (!passwordEncoder.checkPassword(password, user.getPASSWORD())) {
            // Invalid password
            throw new AuthenticationException("Bad credentials.");
        }

        return user;
    }
}