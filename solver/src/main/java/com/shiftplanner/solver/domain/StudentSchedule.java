package com.shiftplanner.solver.domain;

import java.util.Map;
import java.util.Set;

import com.shiftplanner.solver.entities.Student;

public class StudentSchedule {

    // HACK studentとstudentScheduleを継承関係にする

    private Student student;
    private Map<String, Integer> courseMap;
    private Set<Timeslot> dayOffReqs;

    public StudentSchedule() {
    }

    public StudentSchedule(Student student, Map<String, Integer> courseMap, Set<Timeslot> dayOffReqs) {
        this.student = student;
        this.courseMap = courseMap;
        this.dayOffReqs = dayOffReqs;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<String, Integer> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(Map<String, Integer> courseMap) {
        this.courseMap = courseMap;
    }

    public Set<Timeslot> getDayOffReqs() {
        return dayOffReqs;
    }

    public void setDayOffReqs(Set<Timeslot> dayOffReqs) {
        this.dayOffReqs = dayOffReqs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != getClass())
            return false;
        StudentSchedule studentSchedule = (StudentSchedule) obj;
        return this.student.getId().equals(studentSchedule.student.getId());
    }

    @Override
    public int hashCode() {
        return student != null ? student.getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return student.getName();
    }

}
