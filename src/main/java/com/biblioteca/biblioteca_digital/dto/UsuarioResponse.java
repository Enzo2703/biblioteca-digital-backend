package com.biblioteca.biblioteca_digital.dto;



import com.biblioteca.biblioteca_digital.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
	private Integer id;
	private String nombre;
    private String email;
    private String estado;
    private Role role;
    
}
