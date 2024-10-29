package com.shiftplanner.solver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}
