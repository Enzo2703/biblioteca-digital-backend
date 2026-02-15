package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.dto.AuthResponse;
import com.biblioteca.biblioteca_digital.dto.ForgotPasswordRequest;
import com.biblioteca.biblioteca_digital.dto.LoginRequest;
import com.biblioteca.biblioteca_digital.dto.RegisterRequest;
import com.biblioteca.biblioteca_digital.dto.ResetPasswordRequest;
import com.biblioteca.biblioteca_digital.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    authService.forgotPassword(request);
    return ResponseEntity.ok("Si el correo existe, se enviará un enlace de recuperación.");
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            authService.restablecerContrasena(request.getToken(), request.getNuevaContrasena());
            return ResponseEntity.ok("Contraseña restablecida correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
      }

}
