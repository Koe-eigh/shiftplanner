package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.Objects;

import com.shiftplanner.solver.core.scheduling.values.StudentGrade;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class GradeJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  public GradeJpaModel() {}

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static GradeJpaModel fromCoreObject(StudentGrade grade) {
    GradeJpaModel jpaModel = new GradeJpaModel();
    jpaModel.setId(Long.valueOf(grade.getId()));
    jpaModel.setName(grade.getName());
    return jpaModel;
  }

  public StudentGrade toCoreObject() {
    return StudentGrade.fromId(this.id.intValue());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    GradeJpaModel other = (GradeJpaModel) obj;
    return this.id == other.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}
