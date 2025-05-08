// UserService.java
package com.example.usermanagement.application.service;

import com.example.usermanagement.application.port.RoleRepository;
import com.example.usermanagement.application.port.UserRepository;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(String name, String email) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(name, email);
        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User assignRoleToUser(UUID userId, UUID roleId) {
        User user = getUserById(userId);

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.addRole(role);
        return userRepository.save(user);
    }

    public User removeRoleFromUser(UUID userId, UUID roleId) {
        User user = getUserById(userId);

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.removeRole(role);
        return userRepository.save(user);
    }

    private boolean isValidEmail(String email) {
        // Simple email validation
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }
}