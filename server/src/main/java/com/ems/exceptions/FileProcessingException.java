package com.ems.exceptions;

public class FileProcessingException extends RuntimeException{
    public FileProcessingException(String message){
        super(message);
    }
}
