package com.example.paf_ws04.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.paf_ws04.model.exception.ErrorMsg;
import com.example.paf_ws04.model.exception.OrderNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> handleException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        ErrorMsg msg = new ErrorMsg(resp.getStatus(), ex.getMessage(), new Date(), req.getRequestURI());
        return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMsg> handleOrderNotFoundException(OrderNotFoundException ex,
            HttpServletRequest request, HttpServletResponse response) {
        ErrorMsg msg = new ErrorMsg(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date(),
                request.getRequestURI());
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
}
