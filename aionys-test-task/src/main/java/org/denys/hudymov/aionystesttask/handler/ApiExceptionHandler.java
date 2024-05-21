package org.denys.hudymov.aionystesttask.handler;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.denys.hudymov.aionystesttask.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new LinkedList<>();
        var fieldErrors = ex.getBindingResult().getFieldErrors();
        for (var error : fieldErrors) {
            errors.add(error.getDefaultMessage());
        }

        ErrorResponse apiException = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiException);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse apiException = ErrorResponse.builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .statusCode(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiException);
    }
}
