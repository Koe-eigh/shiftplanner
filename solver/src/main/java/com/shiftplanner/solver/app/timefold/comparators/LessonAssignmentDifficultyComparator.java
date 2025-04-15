package com.shiftplanner.solver.app.timefold.comparators;

import java.util.Comparator;

import com.shiftplanner.solver.core.scheduling.entities.Lesson;

public class LessonAssignmentDifficultyComparator implements Comparator<Lesson> {
  @Override
  public int compare(Lesson lesson1, Lesson lesson2) {
    return lesson1.getStudent().getDayOffReqs().size() - lesson2.getStudent().getDayOffReqs().size();
  }
}
