package com.ems.handlers;

import com.ems.dtos.ErrorResponse;
import com.ems.exceptions.*;
import org.openapitools.model.ErrorItem;
import org.openapitools.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // Keep the first error message encountered
                ));

        List<ErrorItem> errorItemList = fieldErrors.entrySet().stream()
                .map(entry -> new ErrorItem(entry.getKey(), entry.getValue()))
                .toList();

        return new ResponseEntity<>(
                new ValidationErrorResponse("Bad request, validation failed for fields", errorItemList),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
            CustomValidationException customValidationException
    ) {
        var candidateErrorResponse = new ErrorResponse();
        candidateErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        candidateErrorResponse.setMessage(String.valueOf(customValidationException.getMessage()));
        candidateErrorResponse.setRequestTime(LocalDateTime.now());
        return new ResponseEntity<>(candidateErrorResponse, HttpStatus.BAD_REQUEST);
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

