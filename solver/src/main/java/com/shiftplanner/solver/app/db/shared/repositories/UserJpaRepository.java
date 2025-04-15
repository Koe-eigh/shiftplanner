package com.shiftplanner.solver.app.db.shared.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.shared.models.UserJpaModel;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaModel, String> {
  Optional<UserJpaModel> findByUsername(String username);
}
