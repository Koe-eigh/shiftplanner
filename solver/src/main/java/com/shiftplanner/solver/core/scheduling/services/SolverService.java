package com.shiftplanner.solver.core.scheduling.services;

import ai.timefold.solver.core.api.solver.SolverManager;

import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.repositories.ITimetableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolverService {
  @Autowired
  private ITimetableRepository timetableRepository;
  @Autowired
  private SolverManager<Timetable, String> solverManager;

  public void solve(String problemId) throws IllegalArgumentException {
    Timetable timetable = timetableRepository.findById(problemId).orElseThrow(() -> new IllegalArgumentException("Timetable not found"));
    solverManager.solveAndListen(timetable.getId().getValue(), timetable, timetableRepository::save);
  }
}
