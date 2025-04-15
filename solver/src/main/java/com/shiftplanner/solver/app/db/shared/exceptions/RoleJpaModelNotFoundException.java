package com.shiftplanner.solver.app.db.shared.exceptions;

public class RoleJpaModelNotFoundException extends RuntimeException {
  public RoleJpaModelNotFoundException() {
    super("Role jpa model not found");
  }

  public RoleJpaModelNotFoundException(String message) {
    super(message);
  }
}
