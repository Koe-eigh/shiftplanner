package com.shiftplanner.solver.app.security.repositories;

import java.util.Optional;
import com.shiftplanner.solver.app.security.models.Role;

public interface IRoleRepository {
  Optional<Role> findByAuthority(String authority);
  void save(Role role);
  Long count();
}
