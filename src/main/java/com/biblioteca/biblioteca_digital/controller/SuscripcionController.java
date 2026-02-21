package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.dto.SuscripcionResponse;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.SuscripcionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;
    private final UsuarioRepository usuarioRepository;

    // 🔹 Activar
    @PostMapping("/activar")
    public ResponseEntity<SuscripcionResponse> activar(Authentication authentication){

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SuscripcionResponse suscripcion =
                suscripcionService.activarSuscripcion(usuario.getId());

        return ResponseEntity.ok(suscripcion);
    }

    // 🔹 Ver mi suscripción
    @GetMapping("/mia")
    public ResponseEntity<SuscripcionResponse> miSuscripcion(Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(
                suscripcionService.obtenerUltimaSuscripcion(usuario.getId())
        );
    }

    // 🔹 Cancelar
    @PutMapping("/cancelar")
    public ResponseEntity<?> cancelar(Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        suscripcionService.cancelarSuscripcion(usuario.getId());

        return ResponseEntity.ok("Suscripción cancelada correctamente");
    }
}