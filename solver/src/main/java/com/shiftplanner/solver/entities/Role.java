package com.shiftplanner.solver.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "role")
public class Role implements GrantedAuthority {
    @Id
    private long roleId;

    private String authority;

    public Role() {}

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(long id, String authority) {
        this.roleId = id;
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
