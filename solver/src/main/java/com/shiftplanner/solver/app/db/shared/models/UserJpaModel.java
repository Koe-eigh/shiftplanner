package com.shiftplanner.solver.app.db.shared.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.shiftplanner.solver.app.db.scheduling.models.StudentJpaModel;
import com.shiftplanner.solver.app.db.scheduling.models.TeacherJpaModel;
import com.shiftplanner.solver.app.db.scheduling.models.TimetableJpaModel;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserJpaModel {
  @Id
  private String id;

  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany
  @JoinColumn(name = "user_id")
  private List<RoleJpaModel> authorities = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private List<TimetableJpaModel> timetables = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private List<TeacherJpaModel> teachers = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private List<StudentJpaModel> students = new ArrayList<>();

  public UserJpaModel() {}

  public UserJpaModel(String id) {
    this.id = id;
  }

  public UserJpaModel(String id, String username, String password, List<RoleJpaModel> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setAuthorities(List<RoleJpaModel> authorities) {
    this.authorities = authorities;
  }

  public List<RoleJpaModel> getAuthorities() {
    return authorities;
  }

  public void setTimetables(List<TimetableJpaModel> timetables) {
    this.timetables = timetables;
  }

  public List<TimetableJpaModel> getTimetables() {
    return timetables;
  }

  public void setTeachers(List<TeacherJpaModel> teachers) {
    this.teachers = teachers;
  }

  public List<TeacherJpaModel> getTeachers() {
    return teachers;
  }

  public void setStudents(List<StudentJpaModel> students) {
    this.students = students;
  }

  public List<StudentJpaModel> getStudents() {
    return students;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    UserJpaModel other = (UserJpaModel) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
