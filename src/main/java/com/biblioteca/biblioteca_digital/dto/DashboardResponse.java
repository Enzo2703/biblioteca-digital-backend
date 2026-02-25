package com.biblioteca.biblioteca_digital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
	
	private long totalUsuarios;
    private long usuariosActivos;
    private long usuariosInactivos;

    private long totalAdmins;
    private long totalLibrarians;
    private long totalReaders;

    private long totalSuscripciones;
    private long suscripcionesActivas;
    private long suscripcionesVencidas;

}
