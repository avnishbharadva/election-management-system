package com.ems.handlers;

import com.ems.dtos.ErrorResponse;
import com.ems.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(
            CandidateNotFoundException candidateNotFoundException
    ) {
        var candidateErrorResponse = new ErrorResponse();
        candidateErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        candidateErrorResponse.setMessage(String.valueOf(candidateNotFoundException.getMessage()));
        candidateErrorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(candidateErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getAllErrors().getFirst().getDefaultMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PartyNotFoundException.class, VoterNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePartyNotFoundException(RuntimeException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(ElectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElectionNotFoundException(RuntimeException runtimeException)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(runtimeException.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateAlreadyExistsException(RuntimeException runtimeException)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(runtimeException.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }


}
