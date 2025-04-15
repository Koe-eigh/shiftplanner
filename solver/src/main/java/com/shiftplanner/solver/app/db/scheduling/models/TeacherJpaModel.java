package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.values.Subject;
import com.shiftplanner.solver.core.scheduling.values.TeacherId;
import com.shiftplanner.solver.core.scheduling.values.TeacherName;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class TeacherJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @ElementCollection
  @CollectionTable(name = "teacher_dayoffs", joinColumns = @JoinColumn(name = "teacher_id"))
  private List<TimeslotJpaModel> dayOffReqs = new ArrayList<TimeslotJpaModel>();

  @ManyToMany
  @JoinTable(name = "teacher_subjects", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
      "teacher_id", "subject_id" }))
  private List<SubjectJpaModel> subjects = new ArrayList<SubjectJpaModel>();

  public TeacherJpaModel() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setDayOffReqs(List<TimeslotJpaModel> dayOffReqs) {
    this.dayOffReqs = dayOffReqs;
  }

  public List<TimeslotJpaModel> getDayOffReqs() {
    return dayOffReqs;
  }

  public void setSubjects(List<SubjectJpaModel> subjects) {
    this.subjects = subjects;
  }

  public List<SubjectJpaModel> getSubjects() {
    return subjects;
  }

  public static TeacherJpaModel fromCoreObject(Teacher teacher) {
    TeacherJpaModel jpaModel = new TeacherJpaModel();
    jpaModel.setId(teacher.getId().getValue() != null ? teacher.getId().getValue() : null);
    jpaModel.setFirstName(teacher.getName().getFirstName());
    jpaModel.setLastName(teacher.getName().getLastName());
    jpaModel.setDayOffReqs(
        teacher.getDayOffReqs().stream().map(timeslot -> TimeslotJpaModel.fromCoreObject(timeslot)).toList());
    jpaModel
        .setSubjects(teacher.getSubjects().stream().map(subject -> SubjectJpaModel.fromCoreObject(subject)).toList());
    return jpaModel;
  }

  public Teacher toCoreObject() {
    Set<Timeslot> dayOffReqs = this.dayOffReqs.stream().map(timeslotJpaModel -> timeslotJpaModel.toCoreObject())
        .collect(Collectors.toSet());
    Set<Subject> subjects = this.subjects.stream().map(subjectJpaModel -> subjectJpaModel.toCoreObject())
        .collect(Collectors.toSet());
    return new Teacher(new TeacherId(this.id), new TeacherName(this.firstName, this.lastName), dayOffReqs, subjects);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    TeacherJpaModel other = (TeacherJpaModel) obj;
    return this.id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
