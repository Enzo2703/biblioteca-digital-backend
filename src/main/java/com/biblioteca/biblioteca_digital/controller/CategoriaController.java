package com.biblioteca.biblioteca_digital.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.biblioteca_digital.service.CategoriaService;
import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.dto.CategoriaDTO;
import com.biblioteca.biblioteca_digital.mapper.CategoriaMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    // --- Endpoint de búsqueda 
    @GetMapping("/search")
    public List<CategoriaDTO> searchCategorias(@RequestParam String q) {
        return categoriaService.getAllCategorias()
                .stream()
                .filter(c -> c.getNombre().toLowerCase().contains(q.toLowerCase()))
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- Obtener todas las categorías activas
    @GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.getAllCategorias()
                .stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- Obtener categoría por ID
    @GetMapping("/{id}")
    public CategoriaDTO getCategoriaById(@PathVariable Integer id) {
        return categoriaMapper.toDTO(categoriaService.getCategoriaById(id));
    }

    // --- Crear nueva categoría
    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public CategoriaDTO createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        return categoriaMapper.toDTO(categoriaService.createCategoria(categoria));
    }

    // --- Actualizar categoría
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public CategoriaDTO updateCategoria(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        return categoriaMapper.toDTO(categoriaService.updateCategoria(id, categoria));
    }

    // --- Eliminar categoría (soft delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public void deleteCategoria(@PathVariable Integer id) {
        categoriaService.deleteCategoria(id);
    }
}