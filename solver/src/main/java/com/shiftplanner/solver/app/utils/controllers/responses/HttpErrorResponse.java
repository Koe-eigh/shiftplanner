package com.shiftplanner.solver.app.utils.controllers.responses;

public class HttpErrorResponse {
  private final int status;
  private final String message;

  public HttpErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatusCode() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
