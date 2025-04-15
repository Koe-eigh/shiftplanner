package com.shiftplanner.solver.core.scheduling.actions.timetables;

import com.shiftplanner.solver.core.scheduling.entities.Timetable;

public interface GetTimetableByIdActionOutputPort {
  void timetable(Timetable timetable);
}
