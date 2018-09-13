package com.mut.feeapi.security.exception;

/**
 * Thrown if an authentication token cannot be refreshed.
 *
 * @author TanagornS
 */
public class AuthenticationTokenRefreshmentException extends RuntimeException {

    public AuthenticationTokenRefreshmentException(String message) {
        super(message);
    }

    public AuthenticationTokenRefreshmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
