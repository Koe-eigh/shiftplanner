package com.shiftplanner.solver.app.adapters.security.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.shared.mappers.RoleMapper;
import com.shiftplanner.solver.app.db.shared.repositories.RoleJpaRepository;
import com.shiftplanner.solver.app.security.models.Role;
import com.shiftplanner.solver.app.security.repositories.IRoleRepository;

@Repository
public class RoleRepository implements IRoleRepository {
    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> findByAuthority(String authority) {
      return roleJpaRepository.findByAuthority(authority).stream().map(RoleMapper::toSecurityObject).findFirst();
    }

    @Override
    public void save(Role role) {
      roleJpaRepository.save(RoleMapper.toModel(role));
    }

    @Override
    public Long count() {
      return roleJpaRepository.count();
    }
}
