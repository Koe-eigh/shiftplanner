package com.shiftplanner.solver.entities;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String grade;
    private Map<String, Teacher> subjectTeacherMap;

    public Student() {}

    public Student(String id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Map<String, Teacher> getSubjectTeacherMap() {
        return subjectTeacherMap;
    }

    public void setSubjectTeacherMap(Map<String, Teacher> subjectTeacherMap) {
        this.subjectTeacherMap = subjectTeacherMap;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null || obj.getClass()!=getClass()) return false;
        Student student = (Student) obj;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
