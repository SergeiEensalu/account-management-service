package com.accountmanagement.application.exception;

import com.accountmanagement.application.response.ApiResponse;

import com.accountmanagement.domain.exception.AccountAlreadyExistsException;
import com.accountmanagement.domain.exception.AccountNotFoundException;
import com.accountmanagement.domain.exception.NothingToUpdateException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        errors
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation constraint failed",
                        errors
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong",
                        List.of("Unexpected error occurred")
                )
        );
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountExists(AccountAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        "Account already exists",
                        List.of(ex.getMessage())
                )
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Account not found", List.of(ex.getMessage()))
        );
    }

    @ExceptionHandler(NothingToUpdateException.class)
    public ResponseEntity<ApiResponse<Void>> handleNothingToUpdate(NothingToUpdateException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Nothing to update", List.of(ex.getMessage()))
        );
    }
}