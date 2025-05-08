
// UserRepositoryAdapter.java
package com.example.usermanagement.infrastructure.persistence.adapter;

import com.example.usermanagement.application.port.UserRepository;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.infrastructure.persistence.entity.RoleJpaEntity;
import com.example.usermanagement.infrastructure.persistence.entity.UserJpaEntity;
import com.example.usermanagement.infrastructure.persistence.repository.UserJpaRepository;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = toJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(userJpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(this::toDomainEntity);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity userJpaEntity = new UserJpaEntity(user.getId(), user.getName(), user.getEmail());

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<RoleJpaEntity> roleJpaEntities = user.getRoles().stream()
                    .map(role -> new RoleJpaEntity(role.getId(), role.getRoleName()))
                    .collect(Collectors.toSet());
            userJpaEntity.setRoles(roleJpaEntities);
        }

        return userJpaEntity;
    }

    private User toDomainEntity(UserJpaEntity userJpaEntity) {
        User user = new User(userJpaEntity.getId(), userJpaEntity.getName(), userJpaEntity.getEmail());

        if (userJpaEntity.getRoles() != null && !userJpaEntity.getRoles().isEmpty()) {
            Set<Role> roles = userJpaEntity.getRoles().stream()
                    .map(roleJpaEntity -> new Role(roleJpaEntity.getId(), roleJpaEntity.getRoleName()))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return user;
    }
}