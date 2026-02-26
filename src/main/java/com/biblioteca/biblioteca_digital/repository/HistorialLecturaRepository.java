package com.biblioteca.biblioteca_digital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.model.HistorialLectura;



@Repository
public interface HistorialLecturaRepository extends JpaRepository<HistorialLectura, Integer> {

	List<HistorialLectura> findByUsuarioIdOrderByFechaAccesoDesc(Integer usuarioId);    

    
}
