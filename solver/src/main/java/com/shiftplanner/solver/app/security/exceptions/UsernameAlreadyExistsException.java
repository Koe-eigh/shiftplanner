package com.shiftplanner.solver.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExistsException extends AuthenticationException{
  public UsernameAlreadyExistsException() {
    super("Username already exists");
  }

  public UsernameAlreadyExistsException(String message) {
    super(message);
  }
}
