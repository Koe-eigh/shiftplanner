package com.shiftplanner.solver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.domain.Timetable;

@Repository
public interface TimetableRepository extends MongoRepository<Timetable, String> {
    
}
