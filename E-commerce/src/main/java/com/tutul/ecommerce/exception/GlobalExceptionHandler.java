package com.tutul.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpServletRequest servletRequest;

    public GlobalExceptionHandler(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @ExceptionHandler(DuplicateProductException.class)
    ResponseEntity<ApiError> HandleDuplicateProductException(DuplicateProductException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                409,// conflict
                e.getMessage(),
                servletRequest.getServletPath(),
                "Duplicate product[conflict-data]"

        );
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<ApiError> HandleOrderNotFoundException(OrderNotFoundException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                404,
                e.getMessage(),
                servletRequest.getServletPath(),
                "Order not found"

        );
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<ApiError> HandleProductNotFoundException(ProductNotFoundException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                404,
                e.getMessage(),
                servletRequest.getServletPath(),
                "Product not found"
        );
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }

    @ExceptionHandler(InvalidProductPriceException.class)
    protected ResponseEntity<ApiError> handleInvalidProductPriceException(InvalidProductPriceException e) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                400,
                e.getMessage(),
                servletRequest.getServletPath(),
                "Invalid product price"
        );
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }
}

