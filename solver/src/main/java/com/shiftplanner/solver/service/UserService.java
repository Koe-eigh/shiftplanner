package com.shiftplanner.solver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shiftplanner.solver.entities.ApplicationUser;
import com.shiftplanner.solver.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("in the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    @PreAuthorize("#userId == authentication.principal.id")
    public ApplicationUser loadUserById(String userId) throws IllegalStateException {
        return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalStateException("user not found"));
    }

}
