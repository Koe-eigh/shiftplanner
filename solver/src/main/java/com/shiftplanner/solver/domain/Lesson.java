package com.shiftplanner.solver.domain;

import java.util.Objects;

import com.shiftplanner.solver.domain.comparators.LessonAssignmentDifficultyComparator;
import com.shiftplanner.solver.domain.comparators.TeacherScheduleStrengthComparator;
import com.shiftplanner.solver.domain.comparators.TimeslotStrengthComparator;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.entity.PlanningPin;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;


@PlanningEntity(difficultyComparatorClass = LessonAssignmentDifficultyComparator.class)
public class Lesson {
    @PlanningId
    private String id;

    private StudentSchedule studentSchedule;
    private String subject;

    @PlanningVariable(strengthComparatorClass = TeacherScheduleStrengthComparator.class)
    private TeacherSchedule teacherSchedule;
    @PlanningVariable(strengthComparatorClass = TimeslotStrengthComparator.class)
    private Timeslot timeslot;

    @PlanningPin
    private boolean pinned;

    public Lesson() {}

    public Lesson(String id, StudentSchedule studentSchedule, String subject, TeacherSchedule teacherSchedule, Timeslot timeslot, boolean pinned) {
        this.id = id;
        this.studentSchedule = studentSchedule;
        this.subject = subject;
        this.teacherSchedule = teacherSchedule;
        this.timeslot = timeslot;
        this.pinned = pinned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentSchedule getStudentSchedule() {
        return studentSchedule;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return subject;
    }

    public void setStudentSchedule(StudentSchedule studentSchedule) {
        this.studentSchedule = studentSchedule;
    }

    public TeacherSchedule getTeacherSchedule() {
        return teacherSchedule;
    }

    public void setTeacherSchedule(TeacherSchedule teacherSchedule) {
        this.teacherSchedule = teacherSchedule;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isPinned() {
        return pinned;
    }

    @Override
    public String toString() {
        return studentSchedule.getStudent() != null ? subject + studentSchedule.getStudent().getGrade() + studentSchedule.getStudent().getName() : subject + null;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null || obj.getClass()!=getClass()) return false;
        Lesson lesson = (Lesson) obj;
        return Objects.equals(id, lesson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentSchedule, subject);
    }
}
