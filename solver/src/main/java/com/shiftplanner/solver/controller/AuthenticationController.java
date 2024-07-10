package com.shiftplanner.solver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftplanner.solver.entities.ApplicationUser;
import com.shiftplanner.solver.entities.dto.LoginResponseDTO;
import com.shiftplanner.solver.entities.dto.RegistrationDTO;
import com.shiftplanner.solver.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> register(@RequestBody RegistrationDTO body) {
        try {
            ApplicationUser user = authenticationService.registerUser(body.getUsername(), body.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().header("Exception", "Username you gave is already used").build();
        }
        
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
