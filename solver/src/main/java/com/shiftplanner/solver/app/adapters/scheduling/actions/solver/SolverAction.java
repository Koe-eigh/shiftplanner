package com.shiftplanner.solver.app.adapters.scheduling.actions.solver;

import org.springframework.beans.factory.annotation.Autowired;

import com.shiftplanner.solver.app.adapters.scheduling.exceptions.NotOwnerTimetableException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.TimetableNotFoundException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.UserNotFoundException;
import com.shiftplanner.solver.app.adapters.scheduling.repositories.TimetableRepository;
import com.shiftplanner.solver.app.adapters.scheduling.repositories.UserRepository;
import com.shiftplanner.solver.app.utils.actions.api.Action;
import com.shiftplanner.solver.core.scheduling.services.SolverService;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.entities.User;
import com.shiftplanner.solver.core.shared.values.UserId;

@Action
public class SolverAction {
  @Autowired
  private SolverService solverService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TimetableRepository timetableRepository;

  public void execute(SolverActionInput input) {
    User user = userRepository.findById(new UserId(input.getUserId()))
        .orElseThrow(() -> new UserNotFoundException("UserId: " + input.getUserId() + " not found."));
    Timetable timetable = timetableRepository.findById(new TimetableId(input.getTimetableId()))
        .orElseThrow(() -> new TimetableNotFoundException("TimetableId: " + input.getTimetableId() + " not found."));
    if (!user.isTimetableOwner(timetable))
      throw new NotOwnerTimetableException();
    solverService.solve(input.getTimetableId());
  }
}
