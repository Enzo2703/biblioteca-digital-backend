package com.biblioteca.biblioteca_digital.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.service.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {
	
	private final LibroService libroService;
	
	@GetMapping
    public List<Libro> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public Libro obtener(@PathVariable Integer id) {
        return libroService.obtener(id);
    }

    @GetMapping("/buscar")
    public List<Libro> buscar(@RequestParam String titulo) {
        return libroService.buscar(titulo);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public Libro crear(@RequestBody Libro libro,
                       Authentication authentication) {

        return libroService.crear(libro, authentication.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public Libro actualizar(@PathVariable Integer id,
                            @RequestBody Libro libro) {

        return libroService.actualizar(id, libro);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public String eliminar(@PathVariable Integer id) {

        libroService.eliminar(id);
        return "Libro eliminado correctamente";
    }

}
