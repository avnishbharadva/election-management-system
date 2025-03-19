package com.ems.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        errorDetail.setProperty("access_denied_reason", "Authentication Failure");
        return errorDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        errorDetail.setProperty("access_denied_reason", "Not authorized");
        return errorDetail;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException ex) {
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, "JWT Token expired");
        errorDetail.setProperty("access_denied_reason", "JWT Token has expired");
        return errorDetail;
    }

    @ExceptionHandler(JwtException.class)
    public ProblemDetail handleJwtException(JwtException ex) {
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        errorDetail.setProperty("access_denied_reason", "Invalid JWT signature or token");
        return errorDetail;
    }

    // @ExceptionHandler(Exception.class)
    // public ProblemDetail handleGenericException(Exception ex) {
    //     ProblemDetail errorDetail = ProblemDetail
    //             .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    //     errorDetail.setProperty("details", ex.getMessage());
    //     return errorDetail;
    // }
}
-