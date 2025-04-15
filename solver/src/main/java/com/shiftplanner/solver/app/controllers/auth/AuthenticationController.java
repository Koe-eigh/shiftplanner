package com.shiftplanner.solver.app.controllers.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftplanner.solver.app.adapters.security.actions.login.LoginAction;
import com.shiftplanner.solver.app.adapters.security.actions.login.LoginActionInput;
import com.shiftplanner.solver.app.adapters.security.actions.login.LoginActionOutput;
import com.shiftplanner.solver.app.adapters.security.actions.registration.RegistrationAction;
import com.shiftplanner.solver.app.adapters.security.actions.registration.RegistrationActionInput;
import com.shiftplanner.solver.app.adapters.security.actions.registration.RegistrationActionOutput;
import com.shiftplanner.solver.app.adapters.security.exceptions.LoginFailedException;
import com.shiftplanner.solver.app.security.exceptions.UsernameAlreadyExistsException;
import com.shiftplanner.solver.app.utils.controllers.responses.HttpErrorResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private RegistrationAction registrationAction;
    @Autowired
    private LoginAction loginAction;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationActionInput input) {
        try {
            RegistrationActionOutput output = new RegistrationActionOutput();
            registrationAction.execute(input, output);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(output);
        } catch (UsernameAlreadyExistsException e) {
            int statusCode = HttpStatus.CONFLICT.value();
            String message = "ユーザーはすでに存在します。";
            return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String message = "サーバーエラーが発生しました。";
            return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginActionInput input) {
        try {
            LoginActionOutput output = new LoginActionOutput();
            loginAction.execute(input, output);
            return ResponseEntity.status(HttpStatus.OK.value()).body(output);
        } catch (LoginFailedException e) {
            logger.error(e.getMessage(), e);
            int statusCode = HttpStatus.UNAUTHORIZED.value();
            String message = "ログインに失敗しました。";
            return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String message = "サーバーエラーが発生しました。";
            return ResponseEntity.status(statusCode).body(new HttpErrorResponse(statusCode, message));
        }
    }
}
