package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.FavoritoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {
	
	private final FavoritoService favoritoService;
	private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> agregar(@RequestParam Integer libroId,
                                     Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        favoritoService.agregarFavorito(libroId, usuario.getId());

        return ResponseEntity.ok("Agregado a favoritos");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id,
                                      Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        favoritoService.eliminarFavorito(id, usuario.getId());

        return ResponseEntity.ok("Eliminado de favoritos");
    }

    @GetMapping("/mios")
    public ResponseEntity<?> misFavoritos(Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(favoritoService.listarMisFavoritos(usuario.getId()));
    }
	

}
