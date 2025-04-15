package com.shiftplanner.solver.app.security.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.app.security.exceptions.UsernameAlreadyExistsException;
import com.shiftplanner.solver.app.security.models.AuthUser;
import com.shiftplanner.solver.app.security.models.Role;
import com.shiftplanner.solver.app.security.repositories.IRoleRepository;
import com.shiftplanner.solver.app.security.repositories.IAuthUserRepository;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private IAuthUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public AuthUser registerUser(String username, String password) throws UsernameAlreadyExistsException {
        if (!userRepository.findByUsername(username).isPresent()) {
            String encodedPassword = passwordEncoder.encode(password);
            Role userRole = roleRepository.findByAuthority("USER").get();

            Set<Role> authorities = new HashSet<>();

            authorities.add(userRole);

            return userRepository
                    .save(new AuthUser(UUID.randomUUID().toString(), username, encodedPassword, authorities));
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    public String loginUser(String username, String password) throws AuthenticationException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        String token = tokenService.generateJwt(auth);

        return token;
    }
}
