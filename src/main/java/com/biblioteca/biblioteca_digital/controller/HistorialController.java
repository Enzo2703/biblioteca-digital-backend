package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.HistorialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialController {
	
	private final HistorialService historialService;
	private final UsuarioRepository usuarioRepository;
	
	@PostMapping
    public ResponseEntity<?> registrar(@RequestParam Integer libroId,
                                       Authentication authentication) {
		String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        historialService.registrarLectura(libroId, usuario.getId());

        return ResponseEntity.ok("Lectura registrada");
        
        
        
    }

    @GetMapping("/mio")
    public ResponseEntity<?> miHistorial(Authentication authentication) {

    	String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(historialService.obtenerMiHistorial(usuario.getId()));
    }

}
