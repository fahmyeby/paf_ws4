package com.example.paf_ws04.model.exception;

import java.util.Date;

public class ErrorMsg {
    private Integer status;
    private String message;
    private Date timestamp;
    private String endpoint;

    public ErrorMsg(Integer status, String message, Date timestamp, String endpoint) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.endpoint = endpoint;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
