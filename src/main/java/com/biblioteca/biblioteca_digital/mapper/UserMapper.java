package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.User;
import com.biblioteca.biblioteca_digital.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole());
        dto.setEnabled(user.getEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        user.setEnabled(dto.getEnabled());
        user.setCreatedAt(dto.getCreatedAt());
        return user;
    }
}

