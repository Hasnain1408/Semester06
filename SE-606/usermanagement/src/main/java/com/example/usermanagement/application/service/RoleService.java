
// RoleService.java
package com.example.usermanagement.application.service;

import com.example.usermanagement.application.port.RoleRepository;
import com.example.usermanagement.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
        // Validate input
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }

        if (roleRepository.existsByRoleName(roleName)) {
            throw new IllegalArgumentException("Role already exists with name: " + roleName);
        }

        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    public Role getRoleById(UUID id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}