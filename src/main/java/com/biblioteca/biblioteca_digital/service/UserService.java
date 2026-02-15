package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.User;
import com.biblioteca.biblioteca_digital.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public User createUser(User user) {
        // Codificar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());

        // Codificar la contraseña si se actualiza
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        user.setFullName(userDetails.getFullName());
        user.setRole(userDetails.getRole());
        user.setEnabled(userDetails.getEnabled());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
    	User user = getUserById(id);
        userRepository.delete(user);
    }
}

