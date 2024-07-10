package com.shiftplanner.solver.repository.relations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shiftplanner.solver.entities.relations.UserToTimetable;

public interface UserToTimetableRepo extends MongoRepository<UserToTimetable, String> {
    
}
