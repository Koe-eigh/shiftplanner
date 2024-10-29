package com.shiftplanner.solver.domain;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.shiftplanner.solver.entities.Teacher;

public class TeacherSchedule {

    //HACK teacherとteacherScheduleを継承関係にする

    @Id
    private String id;
    private Teacher teacher;
    private Set<Timeslot> dayOffReqs;

    public TeacherSchedule() {}

    public TeacherSchedule(String id, Teacher teacher, Set<Timeslot> dayOffReqs) {
        this.id = id;
        this.teacher = teacher;
        this.dayOffReqs = dayOffReqs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Timeslot> getDayOffReqs() {
        return dayOffReqs;
    }

    public void setDayOffReqs(Set<Timeslot> dayOffReqs) {
        this.dayOffReqs = dayOffReqs;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null || obj.getClass()!=getClass()) return false;
        TeacherSchedule teacherSchedule = (TeacherSchedule) obj;
        return Objects.equals(teacher.getName(), teacherSchedule.getTeacher().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher);
    }

    @Override
    public String toString() {
        return teacher.getName();
    }

}
