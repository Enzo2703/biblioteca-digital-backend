package com.biblioteca.biblioteca_digital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
	private Long id;
	private String nombre;
    private String email;
    private String estado;
}
