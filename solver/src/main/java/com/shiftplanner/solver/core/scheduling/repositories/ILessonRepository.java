package com.shiftplanner.solver.core.scheduling.repositories;

import java.util.List;
import java.util.Optional;

import com.shiftplanner.solver.core.scheduling.entities.Lesson;

public interface ILessonRepository {
  Optional<List<Lesson>> findByTimetableId(String timetableId);
  Optional<Lesson> findByLessonId(String lessonId);
  void save(Lesson lesson);
}
