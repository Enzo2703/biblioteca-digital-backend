package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.dto.LoginRequest;
import com.biblioteca.biblioteca_digital.dto.LoginResponse;
import com.biblioteca.biblioteca_digital.model.User;
import com.biblioteca.biblioteca_digital.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        return new LoginResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}

