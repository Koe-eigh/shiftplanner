package com.shiftplanner.solver.app.db.shared.models;

import java.util.Objects;

import com.shiftplanner.solver.app.security.models.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleJpaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "authority", nullable = false)
  private String authority;

  public RoleJpaModel() {}

  public RoleJpaModel(String authority) {
    this.authority = authority;
  }

  public RoleJpaModel(Long id, String authority) {
    this.id = id;
    this.authority = authority;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }

  public static RoleJpaModel fromAuthRole(Role role) {
    return new RoleJpaModel(role.getRoleId(), role.getAuthority());
  }

  public Role toAuthRole() {
    return new Role(id, authority);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    RoleJpaModel other = (RoleJpaModel) obj;
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
