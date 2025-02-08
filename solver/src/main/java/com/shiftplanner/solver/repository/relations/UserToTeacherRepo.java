package com.shiftplanner.solver.repository.relations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.entities.relations.UserToTeacher;

@Repository
public interface UserToTeacherRepo extends MongoRepository<UserToTeacher, String> {
    
}
