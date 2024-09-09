package com.fathom.services.marketplace.controllers.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.naming.AuthenticationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

  private static ErrorResponse of(HttpStatus status, String message) {
    return ErrorResponse.builder()
        .timestamp(LocalDateTime.now(ZoneId.of("UTC")))
        .status(status.getReasonPhrase())
        .message(message)
        .build();
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<ErrorResponse> handleServletRequestBindingException(
      ServletRequestBindingException ex) {
    ErrorResponse res = of(HttpStatus.BAD_REQUEST, ex.getMessage());
    return ResponseEntity.badRequest().body(res);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> hadNoSuchElementException(Exception ex) {
    ErrorResponse res = of(HttpStatus.NOT_FOUND, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handelAuthorizationException(Exception ex) {
    ErrorResponse res = of(HttpStatus.UNAUTHORIZED, ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse res = of(HttpStatus.BAD_REQUEST, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

  @Data
  @Builder
  private static class ErrorResponse {

    private final LocalDateTime timestamp;
    private final String status;
    private final String message;
  }
}
