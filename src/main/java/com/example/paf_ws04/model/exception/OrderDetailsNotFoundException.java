package com.example.paf_ws04.model.exception;

public class OrderDetailsNotFoundException extends RuntimeException {
    public OrderDetailsNotFoundException() {
        super();
    }

    public OrderDetailsNotFoundException(String message) {
        super(message);
    }

    public OrderDetailsNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
