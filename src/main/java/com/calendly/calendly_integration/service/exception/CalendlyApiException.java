package com.calendly.calendly_integration.service.exception;

public class CalendlyApiException extends RuntimeException {

    public CalendlyApiException(String message) {
        super(message);
    }

    public CalendlyApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendlyApiException(Throwable cause) {
        super(cause);
    }
}