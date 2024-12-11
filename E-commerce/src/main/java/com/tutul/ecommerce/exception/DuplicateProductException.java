package com.tutul.ecommerce.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String message) {
         super(message);
    }
}
