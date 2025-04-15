package com.shiftplanner.solver.app.db.shared.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.shared.models.RoleJpaModel;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaModel, Long> {
  Optional<RoleJpaModel> findByAuthority(String authority);
}
