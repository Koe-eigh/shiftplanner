package com.shiftplanner.solver.app.adapters.scheduling.exceptions;

public class TimetableNotFoundException extends RuntimeException {
  public TimetableNotFoundException() {
    super("Timetable not found.");
  }

  public TimetableNotFoundException(String message) {
    super(message);
  }
}
