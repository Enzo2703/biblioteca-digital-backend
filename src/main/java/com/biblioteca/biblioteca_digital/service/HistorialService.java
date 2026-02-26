package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import com.biblioteca.biblioteca_digital.dto.HistorialResponseDto;

public interface HistorialService {
	
	void registrarLectura(Integer libroId, Integer usuarioId);

    List<HistorialResponseDto> obtenerMiHistorial(Integer usuarioId);

}
