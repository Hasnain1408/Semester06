package com.example.usermanagement.application.service;

import com.example.usermanagement.application.port.UserRepository;
import com.example.usermanagement.application.port.RoleRepository;
import com.example.usermanagement.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    void testCreateUser_Success() {
        String name = "John Doe";
        String email = "john@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.createUser(name, email);

        assertNotNull(user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testCreateUser_ThrowsOnInvalidEmail() {
        String name = "John";
        String email = "invalid-email";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(name, email);
        });

        assertTrue(exception.getMessage().contains("Invalid email"));
    }

    @Test
    void testGetUserById_NotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getUserById(id));
        assertEquals("User not found with id: " + id, ex.getMessage());
    }
}
