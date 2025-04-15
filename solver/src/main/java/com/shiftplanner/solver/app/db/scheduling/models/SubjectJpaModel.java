package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.Objects;

import com.shiftplanner.solver.core.scheduling.values.Subject;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class SubjectJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  public SubjectJpaModel() {}

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

  public static SubjectJpaModel fromCoreObject(Subject subject) {
    SubjectJpaModel jpaModel = new SubjectJpaModel();
    jpaModel.setId(Long.valueOf(subject.getId()));
    jpaModel.setName(subject.toString());
    return jpaModel;
  }

  public Subject toCoreObject() {
    return Subject.getSubjectById(this.getId().intValue());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    SubjectJpaModel other = (SubjectJpaModel) obj;
    return this.id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
