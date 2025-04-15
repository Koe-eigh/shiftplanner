package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.Objects;

import com.shiftplanner.solver.core.scheduling.entities.Lesson;
import com.shiftplanner.solver.core.scheduling.entities.Student;
import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.values.Subject;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class LessonJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private StudentJpaModel student;

  @ManyToOne
  @JoinColumn(name = "subject_id", nullable = false)
  private SubjectJpaModel subject;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private TeacherJpaModel teacher;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "year", column = @Column(name = "timeslot_year", nullable = true)),
    @AttributeOverride(name = "month", column = @Column(name = "timeslot_month", nullable = true)),
    @AttributeOverride(name = "day", column = @Column(name = "timeslot_day", nullable = true)),
    @AttributeOverride(name = "hour", column = @Column(name = "timeslot_hour", nullable = true)),
    @AttributeOverride(name = "minute", column = @Column(name = "timeslot_minute", nullable = true)),
    @AttributeOverride(name = "second", column = @Column(name = "timeslot_second", nullable = true)),
    @AttributeOverride(name = "isClosed", column = @Column(name = "timeslot_is_closed", nullable = true)),
  })
  private TimeslotJpaModel timeslot;

  @Column(name = "pinned", nullable = false)
  private boolean pinned;

  public LessonJpaModel() {}

  public LessonJpaModel(Long id, StudentJpaModel student, SubjectJpaModel subject, TeacherJpaModel teacher, TimeslotJpaModel timeslot, boolean pinned) {
    this.id = id;
    this.student = student;
    this.subject = subject;
    this.teacher = teacher;
    this.timeslot = timeslot;
    this.pinned = pinned;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setStudent(StudentJpaModel student) {
    this.student = student;
  }

  public StudentJpaModel getStudent() {
    return student;
  }

  public void setSubject(SubjectJpaModel subject) {
    this.subject = subject;
  }

  public SubjectJpaModel getSubject() {
    return subject;
  }

  public void setTeacher(TeacherJpaModel teacher) {
    this.teacher = teacher;
  }

  public TeacherJpaModel getTeacher() {
    return teacher;
  }

  public void setTimeslot(TimeslotJpaModel timeslot) {
    this.timeslot = timeslot;
  }

  public TimeslotJpaModel getTimeslot() {
    return timeslot;
  }

  public void setPinned(boolean pinned) {
    this.pinned = pinned;
  }

  public boolean isPinned() {
    return pinned;
  }

  public static LessonJpaModel fromCoreObject(Lesson lesson) {
    Long id = lesson.getId();
    StudentJpaModel student = StudentJpaModel.fromCoreObject(lesson.getStudent());
    SubjectJpaModel subject = SubjectJpaModel.fromCoreObject(lesson.getSubject());
    TeacherJpaModel teacher = lesson.getTeacher() != null ? TeacherJpaModel.fromCoreObject(lesson.getTeacher()) : null;
    TimeslotJpaModel timeslot = lesson.getTimeslot() != null ? TimeslotJpaModel.fromCoreObject(lesson.getTimeslot()) : null;
    boolean pinned = lesson.isPinned();
    return new LessonJpaModel(id, student, subject, teacher, timeslot, pinned);
  }

  public Lesson toCoreObject() {
    Long lessonId = id;
    Student student = this.student.toCoreObject();
    Subject subject = this.subject.toCoreObject();
    Teacher teacher = this.teacher != null ? this.teacher.toCoreObject() : null;
    Timeslot timeslot = this.timeslot != null ? this.timeslot.toCoreObject() : null;
    boolean pinned = this.pinned;
    return new Lesson(lessonId, student, subject, teacher, timeslot, pinned);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    TeacherJpaModel other = (TeacherJpaModel) obj;
    return this.id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
