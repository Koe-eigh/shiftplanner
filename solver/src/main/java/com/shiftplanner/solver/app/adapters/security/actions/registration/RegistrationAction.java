package com.shiftplanner.solver.app.adapters.security.actions.registration;

import org.springframework.beans.factory.annotation.Autowired;

import com.shiftplanner.solver.app.security.exceptions.UsernameAlreadyExistsException;
import com.shiftplanner.solver.app.security.models.AuthUser;
import com.shiftplanner.solver.app.security.services.AuthenticationService;
import com.shiftplanner.solver.app.utils.actions.api.Action;

@Action
public class RegistrationAction {
  @Autowired
  AuthenticationService authService;

  public void execute(RegistrationActionInput input, RegistrationActionOutput output) throws UsernameAlreadyExistsException {
    try {
      AuthUser user = authService.registerUser(input.getUsername(), input.getPassword());
      output.setUserId(user.getUserId());
      output.setUsername(user.getUsername());
    } catch (UsernameAlreadyExistsException e) {
      throw e;
    }
  }
}
