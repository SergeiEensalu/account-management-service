package com.accountmanagement.domain.exception;

public class NothingToUpdateException extends RuntimeException {
    public NothingToUpdateException(String message) {
        super(message);
    }
}