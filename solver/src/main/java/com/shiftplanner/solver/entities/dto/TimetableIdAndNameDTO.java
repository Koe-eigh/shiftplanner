package com.shiftplanner.solver.entities.dto;

public class TimetableIdAndNameDTO extends AbstractDTO {
    private String projectId;
    private String name;
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
