package com.ems.exceptions;

public class VoterAlreadyExistException extends RuntimeException {
    public VoterAlreadyExistException(String message) {
        super(message);
    }
}
