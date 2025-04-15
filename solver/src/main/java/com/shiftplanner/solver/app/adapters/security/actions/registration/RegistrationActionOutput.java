package com.shiftplanner.solver.app.adapters.security.actions.registration;

public class RegistrationActionOutput {
  private String userId;
  private String username;

  public RegistrationActionOutput() {}

  public RegistrationActionOutput(String userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
