package com.shiftplanner.solver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.ApplicationUser;

@Repository
public interface UserRepository extends MongoRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByUsername(String username);
    Optional<ApplicationUser> findByUserId(String userId);
}
