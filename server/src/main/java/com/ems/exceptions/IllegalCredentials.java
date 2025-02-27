package com.ems.exceptions;

public class IllegalCredentials extends RuntimeException {
    public IllegalCredentials(String message) {
        super(message);
    }
}
