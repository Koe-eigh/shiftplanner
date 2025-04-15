package com.shiftplanner.solver.app.adapters.security.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.shared.mappers.UserMapper;
import com.shiftplanner.solver.app.db.shared.repositories.UserJpaRepository;
import com.shiftplanner.solver.app.security.models.AuthUser;
import com.shiftplanner.solver.app.security.repositories.IAuthUserRepository;

@Repository
public class AuthUserRepository implements IAuthUserRepository {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public Optional<AuthUser> findByUserId(String userId) {
        return userJpaRepository.findByUsername(userId).stream().map(UserMapper::toSecurityObject).findFirst();
    }

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).stream().map(UserMapper::toSecurityObject).findFirst();
    }

    @Override
    public AuthUser save(AuthUser user) {
        userJpaRepository.save(UserMapper.toModel(user));
        return user;
    }

    @Override
    public Long count() {
        return userJpaRepository.count();
    }
}
