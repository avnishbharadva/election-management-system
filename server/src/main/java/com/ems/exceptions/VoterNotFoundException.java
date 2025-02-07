package com.ems.exceptions;

public class VoterNotFoundException extends RuntimeException {
  public VoterNotFoundException(String message) {
    super(message);
  }
}
