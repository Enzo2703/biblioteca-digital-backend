package com.biblioteca.biblioteca_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.model.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {

	long countByEstado(EstadoSuscripcion estado);
}
