package com.shiftplanner.solver.app.security.models;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
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
