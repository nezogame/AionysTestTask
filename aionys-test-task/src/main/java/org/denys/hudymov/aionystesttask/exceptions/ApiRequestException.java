package org.denys.hudymov.aionystesttask.exceptions;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiRequestException(String message) {
        super(message);
    }
}
