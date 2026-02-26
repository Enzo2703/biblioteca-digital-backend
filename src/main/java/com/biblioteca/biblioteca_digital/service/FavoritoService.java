package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import com.biblioteca.biblioteca_digital.dto.FavoritoResponseDto;

public interface FavoritoService {
	void agregarFavorito(Integer libroId, Integer usuarioId);

    void eliminarFavorito(Integer favoritoId, Integer usuarioId);

    List<FavoritoResponseDto> listarMisFavoritos(Integer usuarioId);

}
