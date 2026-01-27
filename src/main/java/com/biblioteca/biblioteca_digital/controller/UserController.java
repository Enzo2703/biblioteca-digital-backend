package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.model.User;
import com.biblioteca.biblioteca_digital.service.UserService;
import com.biblioteca.biblioteca_digital.dto.UserDTO;
import com.biblioteca.biblioteca_digital.mapper.UserMapper;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Ahora devolviendo DTOs para evitar ciclos infinitos
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAllUsers().stream()
                          .map(UserMapper::toDTO)
                          .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return UserMapper.toDTO(userService.getUserById(id));
    }

    // Métodos POST, PUT, DELETE se mantienen igual
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }
}
