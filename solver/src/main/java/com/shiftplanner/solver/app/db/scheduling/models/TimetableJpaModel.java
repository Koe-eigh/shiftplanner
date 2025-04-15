package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shiftplanner.solver.core.scheduling.entities.Lesson;
import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;
import com.shiftplanner.solver.core.scheduling.values.TimetableName;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "timetables")
public class TimetableJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "timetable_id")
  private List<LessonJpaModel> lessons;

  @ElementCollection
  @CollectionTable(name = "timetable_timeslots", joinColumns = @JoinColumn(name = "timetable_id"))
  private List<TimeslotJpaModel> timeslots;

  @ManyToMany
  @JoinTable(name = "timetable_teachers", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
      "timetable_id", "teacher_id" })
  )
  private List<TeacherJpaModel> teachers;

  @Column(name = "init_score")
  private int initScore;
  @Column(name = "hard_score")
  private int hardScore;
  @Column(name = "soft_score")
  private int softScore;
  @Column(name = "solver_status")
  private String solverStatus;

  public TimetableJpaModel() {
  }

  public TimetableJpaModel(Long id, String name, List<LessonJpaModel> lessons, List<TimeslotJpaModel> timeslots,
      List<TeacherJpaModel> teachers, int initScore, int hardScore, int softScore, String solverStatus) {
    this.id = id;
    this.name = name;
    this.lessons = lessons;
    this.timeslots = timeslots;
    this.teachers = teachers;
    this.initScore = initScore;
    this.hardScore = hardScore;
    this.softScore = softScore;
    this.solverStatus = solverStatus;
  }

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

  public void setLessons(List<LessonJpaModel> lessons) {
    this.lessons = lessons;
  }

  public List<LessonJpaModel> getLessons() {
    return lessons;
  }

  public void setTimeslots(List<TimeslotJpaModel> timeslots) {
    this.timeslots = timeslots;
  }

  public List<TimeslotJpaModel> getTimeslots() {
    return timeslots;
  }

  public void setTeachers(List<TeacherJpaModel> teachers) {
    this.teachers = teachers;
  }

  public List<TeacherJpaModel> getTeachers() {
    return teachers;
  }

  public void setInitScore(int initScore) {
    this.initScore = initScore;
  }

  public int getInitScore() {
    return initScore;
  }

  public void setHardScore(int hardScore) {
    this.hardScore = hardScore;
  }

  public int getHardScore() {
    return hardScore;
  }

  public void setSoftScore(int softScore) {
    this.softScore = softScore;
  }

  public int getSoftScore() {
    return softScore;
  }

  public void setSolverStatus(String solverStatus) {
    this.solverStatus = solverStatus;
  }

  public String getSolverStatus() {
    return solverStatus;
  }

  public static TimetableJpaModel fromCoreObject(Timetable timetable) {
    Long id = timetable.getId() != null ? timetable.getId().getValue() : null;
    String name = timetable.getName().getValue();
    List<LessonJpaModel> lessons = timetable.getLessons() != null
        ? timetable.getLessons().stream().map(LessonJpaModel::fromCoreObject).collect(Collectors.toList())
        : null;
    List<TimeslotJpaModel> timeslots = timetable.getTimeslots() != null
        ? timetable.getTimeslots().stream().map(TimeslotJpaModel::fromCoreObject).collect(Collectors.toList())
        : null;
    List<TeacherJpaModel> teachers = timetable.getTeachers() != null
        ? timetable.getTeachers().stream().map(TeacherJpaModel::fromCoreObject).collect(Collectors.toList())
        : null;
    int initScore = timetable.getScore() != null ? timetable.getScore().initScore() : 0;
    int hardScore = timetable.getScore() != null ? timetable.getScore().hardScore() : 0;
    int softScore = timetable.getScore() != null ? timetable.getScore().softScore() : 0;
    String solverStatus = timetable.getSolverStatus() != null ? timetable.getSolverStatus().name() : null;
    return new TimetableJpaModel(id, name, lessons, timeslots, teachers, initScore, hardScore, softScore, solverStatus);
  }

  public Timetable toCoreObject() {
    TimetableId timetableId = new TimetableId(id);
    TimetableName timetableName = new TimetableName(name);
    List<Lesson> lessons = this.lessons != null ? this.lessons.stream().map(LessonJpaModel::toCoreObject).toList()
        : null;
    List<Timeslot> timeslots = this.timeslots != null
        ? this.timeslots.stream().map(TimeslotJpaModel::toCoreObject).toList()
        : null;
    List<Teacher> teachers = this.teachers != null ? this.teachers.stream().map(TeacherJpaModel::toCoreObject).toList()
        : null;
    HardSoftScore score = HardSoftScore.ofUninitialized(initScore, hardScore, softScore);
    SolverStatus solverStatus = this.solverStatus != null ? SolverStatus.valueOf(this.solverStatus) : null;
    return new Timetable(timetableId, timetableName, lessons, timeslots, teachers, score, solverStatus);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    TimetableJpaModel other = (TimetableJpaModel) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}
