package com.shiftplanner.solver.app.adapters.scheduling.exceptions;

public class NotOwnerTimetableException extends RuntimeException {
  public NotOwnerTimetableException() {
    super("User is not the owner of the timetable.");
  }

  public NotOwnerTimetableException(String message) {
    super(message);
  }
}
