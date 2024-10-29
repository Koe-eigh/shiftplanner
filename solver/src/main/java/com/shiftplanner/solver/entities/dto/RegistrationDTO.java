package com.shiftplanner.solver.entities.dto;

public class RegistrationDTO {
    private String username;
    private String password;

    public RegistrationDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}
