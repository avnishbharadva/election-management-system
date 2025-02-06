package com.ems.exceptions;

public class PartyNotFoundException extends RuntimeException{
    public PartyNotFoundException(String message) {
        super(message);
    }
}
