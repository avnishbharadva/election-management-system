package com.ems.handlers;

import com.ems.dtos.CandidateErrorResponse;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.exceptions.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CandidateErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();

        String errorMessage = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst() // Get the first error message
                .orElse("Validation failed");
        CandidateErrorResponse candidateErrorResponse = new CandidateErrorResponse();
        candidateErrorResponse.setMessage(errorMessage);
        candidateErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        candidateErrorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(candidateErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
