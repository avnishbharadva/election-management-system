package com.ems.handlers;

import com.ems.dtos.ErrorResponse;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.exceptions.VoterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PartyNotFoundException.class, VoterNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePartyNotFoundException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
