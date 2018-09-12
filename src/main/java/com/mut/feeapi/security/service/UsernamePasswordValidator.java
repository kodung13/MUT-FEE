package com.mut.feeapi.security.service;


import com.mut.feeapi.user.domain.User;
import com.mut.feeapi.user.service.UserService;
import com.mut.feeapi.security.exception.AuthenticationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Component for validating user credentials.
 *
 * @author cassiomolin
 */
@ApplicationScoped
public class UsernamePasswordValidator {

    @Inject
    private UserService userService;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Validate username and password.
     *
     * @param username
     * @param password
     * @return
     */
    public User validateCredentials(String username, String password) {

        User user = userService.findByUsernameOrEmail(username);

        if (user == null) {
            // User cannot be found with the given username/email
            throw new AuthenticationException("Bad credentials.");
        }

        if (!user.isActive()) {
            // User is not active
            throw new AuthenticationException("The user is inactive.");
        }

        if (!passwordEncoder.checkPassword(password, user.getPASSWORD())) {
            // Invalid password
            throw new AuthenticationException("Bad credentials.");
        }

        return user;
    }
}