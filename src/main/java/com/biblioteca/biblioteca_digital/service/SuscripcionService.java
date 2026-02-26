package com.biblioteca.biblioteca_digital.service;

public interface SuscripcionService {
	
	void validarSuscripcionActiva(Integer usuarioId);

    void crearOReactivarSuscripcion(Integer usuarioId);

    void cancelarSuscripcion(Integer usuarioId);

    void renovarSuscripcion(Integer usuarioId);

}
