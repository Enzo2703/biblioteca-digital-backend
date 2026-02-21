package com.biblioteca.biblioteca_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.biblioteca_digital.model.Categoria;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Buscar solo categorías activas
    List<Categoria> findByEstado(String estado);
}