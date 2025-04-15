package com.shiftplanner.solver.core.scheduling.services;

import ai.timefold.solver.core.api.solver.SolverManager;

import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.repositories.ITimetableRepository;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SolverService {
  @Autowired
  private ITimetableRepository timetableRepository;
  @Autowired
  private SolverManager<Timetable, Long> solverManager;

  public void solve(Long problemId) throws IllegalArgumentException {
    Timetable timetable = timetableRepository.findById(new TimetableId(problemId)).orElseThrow(() -> new IllegalArgumentException("Timetable not found"));
    solverManager.solveAndListen(timetable.getId().getValue(), timetable, timetableRepository::save);
  }
}
