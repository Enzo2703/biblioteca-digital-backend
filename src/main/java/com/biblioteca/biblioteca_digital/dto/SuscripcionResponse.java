package com.biblioteca.biblioteca_digital.dto;

import java.time.LocalDateTime;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuscripcionResponse {

    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoSuscripcion estado;
    private UsuarioResponse usuario;
}