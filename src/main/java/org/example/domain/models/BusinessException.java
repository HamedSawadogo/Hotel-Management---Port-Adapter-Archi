package org.example.domain.models;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
