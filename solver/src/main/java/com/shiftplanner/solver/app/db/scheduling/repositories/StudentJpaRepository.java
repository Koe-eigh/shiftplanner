package com.shiftplanner.solver.app.db.scheduling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.scheduling.models.StudentJpaModel;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpaModel, Long> {
  
}
