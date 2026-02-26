package com.biblioteca.biblioteca_digital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.model.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

	List<Favorito> findByUsuarioId(Integer usuarioId);

    Optional<Favorito> findByUsuarioIdAndLibroId(Integer usuarioId, Integer libroId);

}
