package com.shiftplanner.solver.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverStatus;

@Document(collection = "timetables")
@PlanningSolution
public class Timetable {
    @Id
    private String id;

    private String name;

    @PlanningEntityCollectionProperty
    private List<Lesson> lessons;
    
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslots;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TeacherSchedule> teacherSchedules;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;

    public Timetable(){}

    public Timetable(String id, String name, List<Lesson> lessons, List<Timeslot> timeslots, List<TeacherSchedule> teacherSchedules) {
        this.id = id;
        this.name = name;
        this.lessons = lessons;
        this.timeslots = timeslots;
        this.teacherSchedules = teacherSchedules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<TeacherSchedule> getTeacherSchedules() {
        return teacherSchedules;
    }

    public void setTeacherSchedules(List<TeacherSchedule> teacherSchedules) {
        this.teacherSchedules = teacherSchedules;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }


}
