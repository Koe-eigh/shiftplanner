package com.shiftplanner.solver.repository.relations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.relations.UserToStudent;

@Repository
public interface UserToStudentRepo extends MongoRepository<UserToStudent, String> {
    
}
