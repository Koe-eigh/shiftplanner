package com.shiftplanner.solver.core.scheduling.entities;

import java.util.Objects;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.entity.PlanningPin;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import com.shiftplanner.solver.app.timefold.comparators.LessonAssignmentDifficultyComparator;
import com.shiftplanner.solver.app.timefold.comparators.TeacherStrengthComparator;
import com.shiftplanner.solver.app.timefold.comparators.TimeslotStrengthComparator;
import com.shiftplanner.solver.core.scheduling.valueObjects.Subject;
import com.shiftplanner.solver.core.scheduling.valueObjects.Timeslot;
import com.shiftplanner.solver.core.scheduling.valueObjects.LessonId;
@PlanningEntity(difficultyComparatorClass = LessonAssignmentDifficultyComparator.class)
public class Lesson {
  @PlanningId
  private LessonId id;

  private Student student;
  private Subject subject;

  @PlanningVariable(strengthComparatorClass = TeacherStrengthComparator.class)
  private Teacher teacher;

  @PlanningVariable(strengthComparatorClass = TimeslotStrengthComparator.class)
  private Timeslot timeslot;

  @PlanningPin
  private boolean pinned;

  public Lesson() {
  }

  public Lesson(LessonId id, Student student, Subject subject, Teacher teacher, Timeslot timeslot, boolean pinned) {
    this.id = id;
    this.student = student;
    this.subject = subject;
    this.teacher = teacher;
    this.timeslot = timeslot;
    this.pinned = pinned;
  }

  public LessonId getId() {
    return this.id;
  }

  public Student getStudent() {
    return this.student;
  }

  public Subject getSubject() {
    return this.subject;
  }

  public Teacher getTeacher() {
    return this.teacher;
  }

  public Timeslot getTimeslot() {
    return this.timeslot;
  }

  public boolean isPinned() {
    return this.pinned;
  }

  public void setPinned(boolean pinned) {
    this.pinned = pinned;
  }

  public boolean isAssigned() {
    return this.timeslot != null && this.teacher != null;
  }

  public boolean isAssignedAtUnavailableTimeslot() {
    return this.timeslot.isClosed()
        || !this.teacher.isAvailableAt(this.timeslot)
        || !this.teacher.isAbleToTeach(this.subject)
        || !this.student.isAvailableAt(this.timeslot);
  }

  @Override
  public String toString() {
    return "Lesson{" +
        "id=" + id +
        ", student=" + student +
        ", subject=" + subject +
        ", teacher=" + teacher +
        ", timeslot=" + timeslot +
        ", pinned=" + pinned +
        "}";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Lesson lesson = (Lesson) obj;
    return this.id.equals(lesson.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, student, subject, teacher, timeslot, pinned);
  }
}
