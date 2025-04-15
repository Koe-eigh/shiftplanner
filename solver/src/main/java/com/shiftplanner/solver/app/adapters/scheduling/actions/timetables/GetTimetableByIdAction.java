package com.shiftplanner.solver.app.adapters.scheduling.actions.timetables;

import org.springframework.beans.factory.annotation.Autowired;

import com.shiftplanner.solver.app.adapters.scheduling.exceptions.NotOwnerTimetableException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.TimetableNotFoundException;
import com.shiftplanner.solver.app.adapters.scheduling.exceptions.UserNotFoundException;
import com.shiftplanner.solver.app.utils.actions.api.Action;
import com.shiftplanner.solver.core.scheduling.actions.timetables.GetTimetableByIdActionInputPort;
import com.shiftplanner.solver.core.scheduling.actions.timetables.GetTimetableByIdActionOutputPort;
import com.shiftplanner.solver.core.scheduling.actions.timetables.IGetTimetableByIdAction;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.entities.User;
import com.shiftplanner.solver.core.scheduling.repositories.ITimetableRepository;
import com.shiftplanner.solver.core.scheduling.repositories.IUserRepository;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;
import com.shiftplanner.solver.core.shared.values.UserId;

@Action
public class GetTimetableByIdAction implements IGetTimetableByIdAction {
  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private ITimetableRepository timetableRepository;

  @Override
  public void execute(GetTimetableByIdActionInputPort input, GetTimetableByIdActionOutputPort output) {
    User user = userRepository.findById(new UserId(input.userId())).orElseThrow(() -> new UserNotFoundException());
    Timetable timetable = timetableRepository.findById(new TimetableId(input.timetableId()))
        .orElseThrow(() -> new TimetableNotFoundException());
    if (!user.isTimetableOwner(timetable))
      throw new NotOwnerTimetableException();
    output.timetable(timetable);
  }
}
