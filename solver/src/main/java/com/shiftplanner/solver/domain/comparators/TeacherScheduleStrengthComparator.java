package com.shiftplanner.solver.domain.comparators;

import java.util.Comparator;

import com.shiftplanner.solver.domain.TeacherSchedule;

public class TeacherScheduleStrengthComparator implements Comparator<TeacherSchedule> {

    @Override
    public int compare(TeacherSchedule o1, TeacherSchedule o2) {
        return -(o1.getDayOffReqs().size() - o2.getDayOffReqs().size());
    }
    
}
