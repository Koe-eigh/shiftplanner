package com.shiftplanner.solver.app.adapters.security.actions.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import com.shiftplanner.solver.app.adapters.scheduling.exceptions.UserNotFoundException;
import com.shiftplanner.solver.app.adapters.security.exceptions.LoginFailedException;
import com.shiftplanner.solver.app.adapters.security.repository.AuthUserRepository;
import com.shiftplanner.solver.app.security.models.AuthUser;
import com.shiftplanner.solver.app.security.services.AuthenticationService;
import com.shiftplanner.solver.app.utils.actions.api.Action;

@Action
public class LoginAction {
  @Autowired
  private AuthenticationService authService;
  @Autowired
  private AuthUserRepository userRepository;

  public void execute(LoginActionInput input, LoginActionOutput output) throws LoginFailedException {
    try {
      String token = authService.loginUser(input.getUsername(), input.getPassword());
      AuthUser user = userRepository.findByUsername(input.getUsername())
          .orElseThrow(() -> new UserNotFoundException());
      output.setUserId(user.getUserId());
      output.setUsername(user.getUsername());
      output.setToken(token);
    } catch (AuthenticationException e) {
      throw new LoginFailedException("Login faild.", e);
    } catch (UserNotFoundException e) {
      throw new LoginFailedException("Login faild.", e);
    }
  }
}
