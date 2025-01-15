package com.example.paf_ws04.model.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException() {
        super();
    }

    public OrderAlreadyExistsException(String message) {
        super(message);
    }

    public OrderAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
