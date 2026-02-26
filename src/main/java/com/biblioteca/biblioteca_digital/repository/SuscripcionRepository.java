package com.biblioteca.biblioteca_digital.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.model.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {

	Optional<Suscripcion> findByUsuarioId(Integer usuarioId);
	
	long countByEstado(EstadoSuscripcion estado);
	
	List<Suscripcion> findByEstadoAndFechaFinBefore(
            EstadoSuscripcion estado,
            LocalDateTime fecha
    );
}
