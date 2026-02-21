package com.biblioteca.biblioteca_digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.model.Suscripcion;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{
	// Trae la última suscripción del usuario (la más reciente)
    Optional<Suscripcion> findTopByUsuarioIdOrderByFechaFinDesc(Long usuarioId);

    // Buscar una activa específica
    Optional<Suscripcion> findByUsuarioIdAndEstado(Long usuarioId, EstadoSuscripcion estado);

}