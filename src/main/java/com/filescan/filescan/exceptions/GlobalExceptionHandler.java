package com.filescan.filescan.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VirusDetectedException.class)
    public ResponseEntity<?> handleVirus(VirusDetectedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleOther(RuntimeException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}
