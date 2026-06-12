package com.lms.exception;

/**
 * Thrown when a business rule is violated, e.g. issuing a book with no
 * available copies. Mapped to HTTP 409 by the global exception handler.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
