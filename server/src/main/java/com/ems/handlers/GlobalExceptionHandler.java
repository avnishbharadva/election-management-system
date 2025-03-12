package com.ems.handlers;

import com.ems.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ErrorItem;
import org.openapitools.model.ErrorResponse;
import org.openapitools.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(
            DataNotFoundException dataNotFoundException
    ) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(String.valueOf(dataNotFoundException.getMessage()));
        errorResponse.setTimestamp(LocalTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        log.info("Method argument not valid exception occurred error message: {}", ex.getMessage());
        var errorItemList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> new ErrorItem(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return new ResponseEntity<>(new ValidationErrorResponse("bad request, validation failed for fields", errorItemList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAlreadyExistException.class, CustomException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCandidateAlreadyExistsException(Exception ex)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalTime.now());

        log.info("errorResponse : {}",errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({IllegalCredentials.class})
    public ResponseEntity<ErrorResponse> illegalCredentials(IllegalCredentials illegalCredentials)
    {
        var errorResponse=new ErrorResponse();
        errorResponse.setMessage(illegalCredentials.getMessage());
        errorResponse.setTimestamp(LocalTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        var errorResponse = new ErrorResponse(
                ex.getMessage(),
                LocalTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
