package com.biblioteca.biblioteca_digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
}
