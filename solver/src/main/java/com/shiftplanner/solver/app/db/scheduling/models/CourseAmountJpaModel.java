package com.shiftplanner.solver.app.db.scheduling.models;

import jakarta.persistence.*;

@Entity
@Table(name = "course_amounts")
public class CourseAmountJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "subject_id")
  private SubjectJpaModel subject;

  @Column(name = "amount", nullable = false)
  private int amount;

  public CourseAmountJpaModel() {}

  public CourseAmountJpaModel(SubjectJpaModel subject, int amount) {
    this.subject = subject;
    this.amount = amount;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setSubject(SubjectJpaModel subject) {
    this.subject = subject;
  }

  public SubjectJpaModel getSubject() {
    return subject;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    CourseAmountJpaModel other = (CourseAmountJpaModel) obj;
    return this.id.equals(other.getId());
  }
}
