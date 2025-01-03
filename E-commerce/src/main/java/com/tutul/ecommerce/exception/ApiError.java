package com.tutul.ecommerce.exception;


import java.time.LocalDateTime;


public class ApiError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ApiError(LocalDateTime now, int status, String message, String path, String error) {
        this.timestamp = now;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;

    }
}