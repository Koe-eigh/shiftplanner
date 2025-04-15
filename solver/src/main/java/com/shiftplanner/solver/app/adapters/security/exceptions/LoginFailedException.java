package com.shiftplanner.solver.app.adapters.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {
  public LoginFailedException() {
    super("Login failed.");
  }

  public LoginFailedException(String message, Exception e) {
    super(message, e);
  }
}
