package com.shiftplanner.solver.entities.relations;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_to_timetable")
public class UserToTimetable {
    @Id
    private String userId;
    private List<String> timetableIds;

    public UserToTimetable() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTimetableIds() {
        return timetableIds;
    }

    public void setTimetableIds(List<String> timetableIds) {
        this.timetableIds = timetableIds;
    }
}
