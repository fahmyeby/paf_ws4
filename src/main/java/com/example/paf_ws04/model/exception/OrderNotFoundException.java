package com.example.paf_ws04.model.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){}

    public OrderNotFoundException(String message){
        super(message);
    }

    public OrderNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }

}
