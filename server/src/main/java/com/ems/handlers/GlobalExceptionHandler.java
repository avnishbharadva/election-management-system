package com.ems.handlers;

import lombok.extern.slf4j.Slf4j;
import com.ems.dtos.ErrorResponse;
import com.ems.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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
    public ResponseEntity<org.openapitools.model.ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        log.info("Method argument not valid exception occurred error message: {}", ex.getMessage());
        var errorItemList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new org.openapitools.model.ErrorItem(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return new ResponseEntity<>(new org.openapitools.model.ValidationErrorResponse("bad request, validation failed for fields", errorItemList), HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponse> handleFileProcessingException(FileProcessingException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("File processing error: " + ex.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
