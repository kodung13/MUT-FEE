package com.mut.feeapi.security.exception;


/**
 * Thrown if an authentication token is invalid.
 *
 * @author TanagornS
 */
public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
