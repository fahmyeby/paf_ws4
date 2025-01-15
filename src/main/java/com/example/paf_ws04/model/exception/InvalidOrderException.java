package com.example.paf_ws04.model.exception;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException() {
        super();
    }

    public InvalidOrderException(String message) {
        super(message);
    }

    public InvalidOrderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
