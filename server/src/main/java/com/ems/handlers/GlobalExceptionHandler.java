package com.ems.handlers;

import com.ems.dtos.ErrorResponse;
import com.ems.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ProblemDetail handleGenericException(DataNotFoundException ex) {
        log.info("DataNotFoundException Occures");
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, "An unexpected error occurred");
        errorDetail.setProperty("details", ex.getMessage());
        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getAllErrors().get(0).getDefaultMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAlreadyExistException.class, CustomException.class})
    public ResponseEntity<ErrorResponse> handleCandidateAlreadyExistsException(Exception ex)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setRequestTime(LocalDateTime.now());

        System.out.println("errorResponse = " + errorResponse);
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
