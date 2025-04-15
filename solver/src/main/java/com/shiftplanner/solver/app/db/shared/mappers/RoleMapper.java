package com.shiftplanner.solver.app.db.shared.mappers;

import com.shiftplanner.solver.app.db.shared.models.RoleJpaModel;
import com.shiftplanner.solver.app.security.models.Role;

public class RoleMapper {
  public static Role toSecurityObject(RoleJpaModel model) {
    return new Role(model.getId(), model.getAuthority());
  }

  public static RoleJpaModel toModel(Role role) {
    return new RoleJpaModel(role.getRoleId(), role.getAuthority());
  }
}
