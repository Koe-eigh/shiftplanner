package com.shiftplanner.solver.app.adapters.scheduling.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
