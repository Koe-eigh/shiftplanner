package com.shiftplanner.solver.core.scheduling.entities;

import com.shiftplanner.solver.core.shared.values.UserId;

import java.util.List;
import java.util.Objects;

public class User {
  private final UserId id;
  private final List<Timetable> timetables;
  private final List<Teacher> teachers;
  private final List<Student> students;

  public User(UserId id, List<Timetable> timetables, List<Teacher> teachers, List<Student> students) {
    this.id = id;
    this.timetables = timetables;
    this.teachers = teachers;
    this.students = students;
  }

  public UserId getId() {
    return id;
  }

  public List<Timetable> getTimetables() {
    return timetables;
  }

  public List<Student> getStudents() {
    return students;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public boolean isTimetableOwner(Timetable timetable) {
    return timetables.contains(timetable);
  }

  public boolean isTeacherOwner(Teacher teacher) {
    return teachers.contains(teacher);
  }

  public boolean isOwnerStudent(Student student) {
    return students.contains(student);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    User other = (User) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
