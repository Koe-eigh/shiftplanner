package com.shiftplanner.solver.domain.comparators;

import java.util.Comparator;

import com.shiftplanner.solver.domain.Lesson;

public class LessonAssignmentDifficultyComparator implements Comparator<Lesson> {

    @Override
    public int compare(Lesson lesson1, Lesson lesson2) {
        return lesson1.getStudentSchedule().getDayOffReqs().size() - lesson2.getStudentSchedule().getDayOffReqs().size();
    }
    
}
