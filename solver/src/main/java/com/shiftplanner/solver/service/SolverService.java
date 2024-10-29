package com.shiftplanner.solver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.domain.Timetable;
import com.shiftplanner.solver.repository.TimetableRepository;

@Service
@Transactional
public class SolverService {
    @Autowired
    private TimetableRepository timetableRepository;

    public Timetable findById(String problemId) {
        return timetableRepository.findById(problemId).orElseThrow(() -> new IllegalStateException("timetable not found"));
    }

    public void save(Timetable timetable) {
        timetable.setScore(null);
        timetableRepository.save(timetable);
    }
}
