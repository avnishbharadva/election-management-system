package com.ems.exceptions;

import com.ems.dtos.CandidateErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CandidateExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CandidateErrorResponse> handleException(
            CandidateNotFoundException candidateNotFoundException
    ){
        CandidateErrorResponse candidateErrorResponse=new CandidateErrorResponse();
        candidateErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        candidateErrorResponse.setMessage(String.valueOf(candidateNotFoundException.getMessage()));
        candidateErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(candidateErrorResponse,HttpStatus.NOT_FOUND);
    }
}
