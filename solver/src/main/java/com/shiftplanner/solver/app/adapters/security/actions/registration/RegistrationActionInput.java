package com.shiftplanner.solver.app.adapters.security.actions.registration;

public class RegistrationActionInput {
  private String username;
  private String password;

  public RegistrationActionInput(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
