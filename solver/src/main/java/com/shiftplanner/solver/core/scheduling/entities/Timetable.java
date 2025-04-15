package com.shiftplanner.solver.core.scheduling.entities;

import java.util.List;
import java.util.Objects;

import com.shiftplanner.solver.core.scheduling.values.Timeslot;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;
import com.shiftplanner.solver.core.scheduling.values.TimetableName;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverStatus;

@PlanningSolution
public class Timetable {
  private TimetableId id;
  private TimetableName name;

  @PlanningEntityCollectionProperty
  private List<Lesson> lessons;

  @ValueRangeProvider
  @ProblemFactCollectionProperty
  private List<Timeslot> timeslots;

  @ValueRangeProvider
  @ProblemFactCollectionProperty
  private List<Teacher> teachers;

  @PlanningScore
  private HardSoftScore score;

  private SolverStatus solverStatus;

  public Timetable() {}

  public Timetable(TimetableName name) {
    this.name = name;
  }

  public Timetable(TimetableId id, TimetableName name, List<Lesson> lessons, List<Timeslot> timeslots, List<Teacher> teachers) {
    this.id = id;
    this.name = name;
    this.lessons = lessons;
    this.timeslots = timeslots;
    this.teachers = teachers;
  }

  public Timetable(TimetableId id, TimetableName name, List<Lesson> lessons, List<Timeslot> timeslots, List<Teacher> teachers, HardSoftScore score, SolverStatus solverStatus) {
    this.id = id;
    this.name = name;
    this.lessons = lessons;
    this.timeslots = timeslots;
    this.teachers = teachers;
    this.score = score;
    this.solverStatus = solverStatus;
  }

  public TimetableId getId() {
    return id;
  }

  public TimetableName getName() {
    return name;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  public List<Timeslot> getTimeslots() {
    return timeslots;
  }

  public void setTimeslots(List<Timeslot> timeslots) {
    this.timeslots = timeslots;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    Timetable other = (Timetable) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Timetabl: { id: " + this.id + ", name: " + this.name + " }";
  }
}
