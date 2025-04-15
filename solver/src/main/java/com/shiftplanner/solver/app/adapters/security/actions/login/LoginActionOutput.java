package com.shiftplanner.solver.app.adapters.security.actions.login;

public class LoginActionOutput {
  private String userId;
  private String username;
  private String token;

  public LoginActionOutput() {}

  public LoginActionOutput(String userId, String username, String token) {
    this.userId = userId;
    this.username = username;
    this.token = token;
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

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
