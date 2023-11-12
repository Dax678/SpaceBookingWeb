package com.example.spacebookingweb.Configuration;

import com.example.spacebookingweb.Controller.ReservationController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ReservationController.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("MethodArgumentNotValidException: Unknown validation error.");

        LOGGER.log(Level.WARN, errors);

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationErrors(ConstraintViolationException ex) {
        String error = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("ConstraintViolationException: Unknown validation error.");

        LOGGER.log(Level.WARN, error);

        return new ResponseEntity<>(getErrorsMap(error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
        String errors = ex.getMessage();
        LOGGER.log(Level.ERROR, errors);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
//        String error = ex.getMessage();
//        return new ResponseEntity<>(getErrorsMap(error), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private Map<String, String> getErrorsMap(String error) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", error);
            return errorResponse;
    }
}
