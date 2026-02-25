package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.dto.RegisterRequest;
import com.biblioteca.biblioteca_digital.enums.Role;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;

    @PostMapping("/crear-librarian")
    public ResponseEntity<?> crearLibrarian(@RequestBody RegisterRequest request) {

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRole(Role.LIBRARIAN);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Librarian creado correctamente");
    }
    
    
 // 1. Ver usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(adminService.listarUsuarios());
    }

    // 2. Cambiar rol
    @PutMapping("/usuarios/{id}/rol")
    public ResponseEntity<?> cambiarRol(@PathVariable Integer id, @RequestParam Role role) {
        adminService.cambiarRol(id, role);
        return ResponseEntity.ok("Rol actualizado");
    }

    // 3. Activar / desactivar
    @PutMapping("/usuarios/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam String estado) {
        adminService.cambiarEstado(id, estado);
        return ResponseEntity.ok("Estado actualizado");
    }
    
    //pagin ainicio
    @GetMapping("/dashboard")
    public ResponseEntity<?> obtenerDashboard() {
        return ResponseEntity.ok(adminService.obtenerMetricas());
    }
}
