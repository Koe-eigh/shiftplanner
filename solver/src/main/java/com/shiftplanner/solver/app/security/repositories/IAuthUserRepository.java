package com.shiftplanner.solver.app.security.repositories;

import java.util.Optional;
import com.shiftplanner.solver.app.security.models.AuthUser;

public interface IAuthUserRepository {
  Optional<AuthUser> findByUsername(String username);
  Optional<AuthUser> findByUserId(String userId);
  AuthUser save(AuthUser user);
  Long count();
}
