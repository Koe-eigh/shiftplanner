package com.shiftplanner.solver.core.scheduling.entities;

import java.util.Objects;
import java.util.Map;
import java.util.Set;

import com.shiftplanner.solver.core.scheduling.valueObjects.StudentId;
import com.shiftplanner.solver.core.scheduling.valueObjects.StudentName;
import com.shiftplanner.solver.core.scheduling.valueObjects.StudentGrade;
import com.shiftplanner.solver.core.scheduling.valueObjects.Subject;
import com.shiftplanner.solver.core.scheduling.valueObjects.Timeslot;

public class Student {
  private StudentId id;
  private StudentName name;
  private StudentGrade grade;
  private Set<Timeslot> dayOffReqs;
  private Map<Subject, Integer> courseAmountMap;
  private Map<Teacher, Subject> teachersInChargeMap;

  public Student() {}

  public Student(StudentId id, StudentName name, StudentGrade grade, Set<Timeslot> dayOffReqs, Map<Subject, Integer> courseAmountMap, Map<Teacher, Subject> teachersInChargeMap) {
    this.id = id;
    this.name = name;
    this.grade = grade;
    this.dayOffReqs = dayOffReqs;
    this.courseAmountMap = courseAmountMap;
    this.teachersInChargeMap = teachersInChargeMap;
  }

  public StudentId getId() {
    return this.id;
  }

  public StudentName getName() {
    return this.name;
  }

  public StudentGrade getGrade() {
    return this.grade;
  }

  public Set<Timeslot> getDayOffReqs() {
    return this.dayOffReqs;
  }

  public void addDayOffReq(Timeslot timeslot) {
    this.dayOffReqs.add(timeslot);
  }

  public Map<Subject, Integer> getCourseAmountMap() {
    return this.courseAmountMap;
  }

  public void addCourseAmount(Subject subject, int amount) {
    this.courseAmountMap.put(subject, amount);
  }

  public Map<Teacher, Subject> getTeachersInChargeMap() {
    return this.teachersInChargeMap;
  }

  public void addTeacherInCharge(Teacher teacher, Subject subject) {
    this.teachersInChargeMap.put(teacher, subject);
  }

  public boolean isAvailableAt(Timeslot timeslot) {
    return this.dayOffReqs.contains(timeslot);
  }

  public int getCourseAmountOf(Subject subject) {
    return this.courseAmountMap.get(subject);
  }

  public boolean hasRegularLesson(Subject subject) {
    return this.teachersInChargeMap.values().contains(subject);
  }

  public boolean isTaughtBy(Teacher teacher) {
    return this.teachersInChargeMap.containsKey(teacher);
  }

  public boolean isTaughtBy(Teacher teacher, Subject subject) {
    return this.teachersInChargeMap.get(teacher).equals(subject);
  }

  public Subject getSubjectTaughtBy(Teacher teacher) {
    return this.teachersInChargeMap.get(teacher);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Student student = (Student) obj;
    return this.id == student.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Student{" +
            "id=" + id +
            ", name=" + name +
            '}';
  }
}
