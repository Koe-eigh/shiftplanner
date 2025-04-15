package com.shiftplanner.solver.app.db.shared.exceptions;

public class UserJpaModelNotFoundException extends RuntimeException {
  public UserJpaModelNotFoundException() {
    super("User jpa model not found.");
  }
  public UserJpaModelNotFoundException(String message) {
    super(message);
  }
}
