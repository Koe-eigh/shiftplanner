package com.shiftplanner.solver.app.adapters.scheduling.actions.timetables;

import com.shiftplanner.solver.core.scheduling.actions.timetables.GetTimetableByIdActionInputPort;

public class GetTimetableByIdActionInput implements GetTimetableByIdActionInputPort {
  private final String userId;
  private final Long timetableId;

  public GetTimetableByIdActionInput(String userId, Long timetableId) {
    this.userId = userId;
    this.timetableId = timetableId;
  }

  @Override
  public String userId() {
    return userId;
  }

  @Override
  public Long timetableId() {
    return timetableId;
  }
}
