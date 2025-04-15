package com.shiftplanner.solver.core.scheduling.repositories;

import java.util.Optional;

import com.shiftplanner.solver.core.scheduling.entities.User;
import com.shiftplanner.solver.core.shared.values.UserId;
import com.shiftplanner.solver.core.utils.repositories.IBaseRepository;

public interface IUserRepository extends IBaseRepository<User, UserId> {
  Optional<User> findByUsername(String username);
}
