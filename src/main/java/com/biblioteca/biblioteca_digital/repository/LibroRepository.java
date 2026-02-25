package com.biblioteca.biblioteca_digital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

	List<Libro> findByEstado(String estado);

    Optional<Libro> findByIdAndEstado(Integer id, String estado);

    List<Libro> findByTituloContainingIgnoreCaseAndEstado(String titulo, String estado);

    List<Libro> findByCategoria_IdAndEstado(Integer categoriaId, String estado);
}
