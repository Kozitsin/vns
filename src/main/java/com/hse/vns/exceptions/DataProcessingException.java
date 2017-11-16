package com.hse.vns.exceptions;


public class DataProcessingException extends RuntimeException {
    public DataProcessingException() {
    }

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(Throwable cause) {
        super(cause);
    }

    public DataProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
