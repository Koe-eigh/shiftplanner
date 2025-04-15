package com.shiftplanner.solver.app.timefold.comparators;

import java.util.Comparator;

import com.shiftplanner.solver.core.scheduling.entities.Teacher;

public class TeacherStrengthComparator implements Comparator<Teacher> {
  @Override
  public int compare(Teacher o1, Teacher o2) {
    return -(o1.getDayOffReqs().size() - o2.getDayOffReqs().size());
  }
}
