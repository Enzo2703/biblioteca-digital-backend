package com.biblioteca.biblioteca_digital.service;

import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Traer solo categorías activas
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .filter(c -> "A".equals(c.getEstado())) // filtra solo activas
                .collect(Collectors.toList());
    }

    public Categoria getCategoriaById(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        if ("I".equals(categoria.getEstado())) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }
        return categoria;
    }

    public Categoria createCategoria(Categoria categoria) {
        categoria.setEstado("A"); // activo por defecto
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Integer id, Categoria categoriaDetails) {
        Categoria categoria = getCategoriaById(id);
        categoria.setNombre(categoriaDetails.getNombre());
        categoria.setDescripcion(categoriaDetails.getDescripcion());
        return categoriaRepository.save(categoria);
    }

    // Soft delete: cambiar estado a 'I'
    public void deleteCategoria(Integer id) {
        Categoria categoria = getCategoriaById(id);
        categoria.setEstado("I"); // inactiva
        categoriaRepository.save(categoria);
    }
}