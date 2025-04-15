package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.shiftplanner.solver.core.scheduling.entities.Student;
import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.values.StudentGrade;
import com.shiftplanner.solver.core.scheduling.values.StudentId;
import com.shiftplanner.solver.core.scheduling.values.StudentName;
import com.shiftplanner.solver.core.scheduling.values.Subject;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class StudentJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @ManyToOne(optional = false)
  @JoinColumn(name = "grade_id")
  private GradeJpaModel grade;

  @ElementCollection
  @CollectionTable(name = "student_dayoffs", joinColumns = @JoinColumn(name = "student_id"))
  private List<TimeslotJpaModel> dayOffReqs;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CourseAmountJpaModel> courseAmounts;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RegularLessonJpaModel> regularLessons;

  public StudentJpaModel() {
  }

  public StudentJpaModel(Long id, String firstName, String lastName, GradeJpaModel grade,
      List<TimeslotJpaModel> dayOffReqs, List<CourseAmountJpaModel> courseAmounts,
      List<RegularLessonJpaModel> regularLessons) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.grade = grade;
    this.dayOffReqs = dayOffReqs;
    this.courseAmounts = courseAmounts;
    this.regularLessons = regularLessons;
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

  public void setGrade(GradeJpaModel grade) {
    this.grade = grade;
  }

  public GradeJpaModel getGrade() {
    return grade;
  }

  public void setDayOffReqs(List<TimeslotJpaModel> dayOffReqs) {
    this.dayOffReqs = dayOffReqs;
  }

  public List<TimeslotJpaModel> getDayOffReqs() {
    return dayOffReqs;
  }

  public void setCourseAmounts(List<CourseAmountJpaModel> courseAmounts) {
    this.courseAmounts = courseAmounts;
  }

  public List<CourseAmountJpaModel> getCourseAmounts() {
    return courseAmounts;
  }

  public void setRegularLessons(List<RegularLessonJpaModel> regularLessons) {
    this.regularLessons = regularLessons;
  }

  public List<RegularLessonJpaModel> getRegularLessons() {
    return regularLessons;
  }

  public static StudentJpaModel fromCoreObject(Student student) {
    Long id = student.getId().getValue();
    String firstName = student.getName().getFirstName();
    String lastName = student.getName().getLastName();
    GradeJpaModel grade = GradeJpaModel.fromCoreObject(student.getGrade());
    List<TimeslotJpaModel> dayOffreqs = student.getDayOffReqs() != null ? student.getDayOffReqs().stream()
        .map(timeslot -> TimeslotJpaModel.fromCoreObject(timeslot)).collect(Collectors.toList()) : null;
    List<CourseAmountJpaModel> courseAmounts = student.getCourseAmountMap() != null
        ? student.getCourseAmountMap().entrySet().stream()
            .map(entry -> new CourseAmountJpaModel(SubjectJpaModel.fromCoreObject(entry.getKey()), entry.getValue()))
            .collect(Collectors.toList())
        : null;
    List<RegularLessonJpaModel> regularLessons = student
        .getTeachersInChargeMap() != null
            ? student.getTeachersInChargeMap().entrySet().stream()
                .map(entry -> new RegularLessonJpaModel(TeacherJpaModel.fromCoreObject(entry.getKey()),
                    SubjectJpaModel.fromCoreObject(entry.getValue())))
                .collect(Collectors.toList())
            : null;
    return new StudentJpaModel(id, firstName, lastName, grade, dayOffreqs, courseAmounts, regularLessons);
  }

  public Student toCoreObject() {
    StudentId studentId = id != null ? new StudentId(id) : null;
    StudentName studentName = new StudentName(firstName, lastName);
    StudentGrade studentGrade = StudentGrade.fromId(this.grade.getId().intValue());
    Set<Timeslot> dayOffReqs = this.dayOffReqs != null ? this.dayOffReqs.stream().map(timeslotJpaModel -> timeslotJpaModel.toCoreObject())
        .collect(Collectors.toSet()) : null;
    Map<Subject, Integer> courseAmountMap = this.courseAmounts != null ? this.courseAmounts.stream()
        .collect(Collectors.toMap(courseAmountJpaModel -> courseAmountJpaModel.getSubject().toCoreObject(),
            courseAmountJpaModel -> courseAmountJpaModel.getAmount())) : null;
    Map<Teacher, Subject> teacherInChargeMap = this.regularLessons != null ? this.regularLessons.stream()
        .collect(Collectors.toMap(regularLessonJpaModel -> regularLessonJpaModel.getTeacher().toCoreObject(),
            regularLessonJpaModel -> regularLessonJpaModel.getSubject().toCoreObject())) : null;
    return new Student(studentId, studentName, studentGrade, dayOffReqs, courseAmountMap, teacherInChargeMap);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    StudentJpaModel other = (StudentJpaModel) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
