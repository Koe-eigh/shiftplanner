package com.shiftplanner.solver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    
}
