package com.shiftplanner.solver.core.scheduling.entities;

import java.util.Objects;
import java.util.Set;

import com.shiftplanner.solver.core.scheduling.values.Subject;
import com.shiftplanner.solver.core.scheduling.values.TeacherId;
import com.shiftplanner.solver.core.scheduling.values.TeacherName;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;

public class Teacher {
  private TeacherId id;
  private TeacherName name;
  private Set<Timeslot> dayOffReqs;
  private Set<Subject> subjects;

  public Teacher() {}

  public Teacher(TeacherId id, TeacherName name, Set<Timeslot> dayOffReqs, Set<Subject> subjects) {
    this.id = id;
    this.name = name;
    this.dayOffReqs = dayOffReqs;
    this.subjects = subjects;
  }

  public TeacherId getId() {
    return this.id;
  }

  public TeacherName getName() {
    return this.name;
  }

  public Set<Timeslot> getDayOffReqs() {
    return this.dayOffReqs;
  }

  public Set<Subject> getSubjects() {
    return this.subjects;
  }

  public void addDayOffReq(Timeslot timeslot) {
    this.dayOffReqs.add(timeslot);
  }

  public void addSubject(Subject subject) {
    this.subjects.add(subject);
  }

  public boolean isAvailableAt(Timeslot timeslot) {
    return !this.dayOffReqs.contains(timeslot);
  }

  public boolean isAbleToTeach(Subject subject) {
    return this.subjects.contains(subject);
  }

  @Override
  public String toString() {
    return "Teacher{" +
        "id=" + id +
        ", name=" + name +
        ", dayOffReqs=" + dayOffReqs +
        ", subjects=" + subjects +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Teacher teacher = (Teacher) o;
    return id.equals(teacher.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, dayOffReqs, subjects);
  }
}

