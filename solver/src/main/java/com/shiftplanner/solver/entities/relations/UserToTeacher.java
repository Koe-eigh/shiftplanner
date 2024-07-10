package com.shiftplanner.solver.entities.relations;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_to_teacher")
public class UserToTeacher {
    @Id
    private String userId;
    private List<String> teacherIds;

    public UserToTeacher() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<String> teacherIds) {
        this.teacherIds = teacherIds;
    }
}
