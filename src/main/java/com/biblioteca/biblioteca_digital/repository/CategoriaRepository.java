package com.biblioteca.biblioteca_digital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	List<Categoria> findByEstado(String estado);
	Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
