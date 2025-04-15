package com.shiftplanner.solver.app.adapters.scheduling.actions.timetables;

import com.shiftplanner.solver.core.scheduling.actions.timetables.GetTimetableByIdActionOutputPort;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;

public class GetTimetableByIdActionOutput implements GetTimetableByIdActionOutputPort {
  private Timetable timetable;

  public GetTimetableByIdActionOutput() {}

  @Override
  public void timetable(Timetable timetable) {
    this.timetable = timetable;
  }

  public Timetable getTimetable() {
    return timetable;
  }
}
