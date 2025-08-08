package com.douglas.api.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Global exception handler for REST API errors. */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DeviceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(DeviceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(ex.getMessage(), 404));
  }

  @ExceptionHandler(DeviceInUseException.class)
  public ResponseEntity<Map<String, Object>> handleInUse(DeviceInUseException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(ex.getMessage(), 400));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildError("Unexpected error: " + ex.getMessage(), 500));
  }

  private Map<String, Object> buildError(String message, int status) {
    return Map.of(
        "timestamp", Instant.now().toString(),
        "status", status,
        "error", message);
  }
}
