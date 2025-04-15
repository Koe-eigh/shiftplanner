package com.shiftplanner.solver.app.db.shared.mappers;

import java.util.stream.Collectors;

import com.shiftplanner.solver.app.db.shared.models.UserJpaModel;
import com.shiftplanner.solver.app.security.models.AuthUser;
import com.shiftplanner.solver.app.security.models.Role;

public class UserMapper {
  public static AuthUser toSecurityObject(UserJpaModel model) {
    return new AuthUser(model.getId(), model.getUsername(), model.getPassword(), model.getAuthorities().stream().map(RoleMapper::toSecurityObject).collect(Collectors.toSet()));
  }

  public static UserJpaModel toModel(AuthUser user) {
    return new UserJpaModel(user.getUserId(), user.getUsername(), user.getPassword(), user.getAuthorities().stream().map(role -> RoleMapper.toModel((Role)role)).toList());
  }
}
