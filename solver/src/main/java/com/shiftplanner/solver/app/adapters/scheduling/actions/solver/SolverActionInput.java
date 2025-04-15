package com.shiftplanner.solver.app.adapters.scheduling.actions.solver;

public class SolverActionInput {
  private final Long timetableId;
  private final String userId;
  public SolverActionInput(Long timetableId, String userId) {
    this.timetableId = timetableId;
    this.userId = userId;
  }

  public Long getTimetableId() {
    return timetableId;
  }

  public String getUserId() {
    return userId;
  }
}
