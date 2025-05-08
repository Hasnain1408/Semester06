// RoleResponseDto.java
package com.example.usermanagement.infrastructure.controller.dto;

import java.util.UUID;

public class RoleResponseDto {
    private UUID id;
    private String roleName;

    public RoleResponseDto() {
    }

    public RoleResponseDto(UUID id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}