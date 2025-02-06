package com.ems.handlers;

import com.ems.dtos.CandidateErrorResponse;
import com.ems.exceptions.CandidateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<CandidateErrorResponse> handleException(
            CandidateNotFoundException candidateNotFoundException
    ){
        CandidateErrorResponse candidateErrorResponse=new CandidateErrorResponse();
        candidateErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        candidateErrorResponse.setMessage(String.valueOf(candidateNotFoundException.getMessage()));
        candidateErrorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(candidateErrorResponse,HttpStatus.NOT_FOUND);
    }
}
