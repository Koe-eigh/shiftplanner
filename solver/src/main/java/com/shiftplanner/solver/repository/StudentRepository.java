package com.shiftplanner.solver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    
}
