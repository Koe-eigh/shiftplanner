package com.shiftplanner.solver.core.scheduling.repositories;

import java.util.Optional;
import java.util.UUID;

import com.shiftplanner.solver.core.scheduling.entities.Timetable;

public interface ITimetableRepository {
  Optional<Timetable> findByTimetableId(UUID timetableId);
  void save(Timetable timetable);
}
