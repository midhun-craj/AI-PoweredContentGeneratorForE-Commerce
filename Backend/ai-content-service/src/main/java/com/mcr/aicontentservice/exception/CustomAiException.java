package com.mcr.aicontentservice.exception;

public class CustomAiException extends RuntimeException {
    public CustomAiException(String message) {
        super(message);
    }

    public CustomAiException(String message, Throwable cause) {
        super(message, cause);
    }

}
