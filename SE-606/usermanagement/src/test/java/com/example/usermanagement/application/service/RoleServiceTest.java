// RoleServiceTest.java
package com.example.usermanagement.application.service;

import com.example.usermanagement.application.port.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    void createRole_WithValidName_ShouldSucceed() {
        // Arrange
        String roleName = "ADMIN";
        Role expectedRole = new Role(roleName);

        when(roleRepository.existsByRoleName(roleName)).thenReturn(false);
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        // Act
        Role result = roleService.createRole(roleName);

        // Assert
        assertNotNull(result);
        assertEquals(roleName, result.getRoleName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void createRole_WithEmptyName_ShouldThrowException() {
        // Arrange
        String roleName = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> roleService.createRole(roleName));
        assertEquals("Role name cannot be empty", exception.getMessage());
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void createRole_WithExistingName_ShouldThrowException() {
        // Arrange
        String roleName = "ADMIN";

        when(roleRepository.existsByRoleName(roleName)).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> roleService.createRole(roleName));
        assertEquals("Role already exists with name: " + roleName, exception.getMessage());
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void getRoleById_WithExistingId_ShouldReturnRole() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        Role expectedRole = new Role(roleId, "ADMIN");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(expectedRole));

        // Act
        Role result = roleService.getRoleById(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(roleId, result.getId());
        assertEquals("ADMIN", result.getRoleName());
    }

    @Test
    void getRoleById_WithNonExistingId_ShouldThrowException() {
        // Arrange
        UUID roleId = UUID.randomUUID();

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> roleService.getRoleById(roleId));
        assertEquals("Role not found with id: " + roleId, exception.getMessage());
    }

    @Test
    void getAllRoles_ShouldReturnAllRoles() {
        // Arrange
        Role role1 = new Role(UUID.randomUUID(), "ADMIN");
        Role role2 = new Role(UUID.randomUUID(), "USER");
        List<Role> expectedRoles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(expectedRoles);

        // Act
        List<Role> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        assertEquals("USER", result.get(1).getRoleName());
    }
}