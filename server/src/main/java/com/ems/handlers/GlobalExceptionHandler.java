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

    @ExceptionHandler(CandidateAssociatedException.class)
    public ResponseEntity<ErrorResponse> handleAssociatedCandidate(
            CandidateAssociatedException candidateAssociatedException
    ){
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT .value());
        errorResponse.setMessage(String.valueOf(candidateAssociatedException.getMessage()));
        errorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(
            DataNotFoundException dataNotFoundException
    ) {
        var candidateErrorResponse = new ErrorResponse();
        candidateErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        candidateErrorResponse.setMessage(String.valueOf(dataNotFoundException.getMessage()));
        candidateErrorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(candidateErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getAllErrors().get(0).getDefaultMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAlreadyExistException.class})
    public ResponseEntity<ErrorResponse> handleCandidateAlreadyExistsException(DataAlreadyExistException dataAlreadyExistException)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(dataAlreadyExistException.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({IllegalCredentials.class})
    public ResponseEntity<ErrorResponse> illegalCredentials(IllegalCredentials illegalCredentials)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(illegalCredentials.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



}
