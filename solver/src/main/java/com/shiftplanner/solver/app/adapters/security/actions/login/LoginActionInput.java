package com.shiftplanner.solver.app.adapters.security.actions.login;

public class LoginActionInput {
  private final String username;
  private final String password;

  public LoginActionInput(String username, String password) {
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
