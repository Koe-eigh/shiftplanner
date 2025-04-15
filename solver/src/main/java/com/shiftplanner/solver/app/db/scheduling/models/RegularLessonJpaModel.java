package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "regular_lessons")
public class RegularLessonJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "teacher_id")
  private TeacherJpaModel teacher;

  @ManyToOne(optional = false)
  @JoinColumn(name = "subject_id")
  private SubjectJpaModel subject;

  public RegularLessonJpaModel() {}

  public RegularLessonJpaModel(TeacherJpaModel teacher, SubjectJpaModel subject) {
    this.teacher = teacher;
    this.subject = subject;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setTeacher(TeacherJpaModel teacher) {
    this.teacher = teacher;
  }

  public TeacherJpaModel getTeacher() {
    return teacher;
  }

  public void setSubject(SubjectJpaModel subject) {
    this.subject = subject;
  }

  public SubjectJpaModel getSubject() {
    return subject;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    RegularLessonJpaModel other = (RegularLessonJpaModel) obj;
    return this.id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
