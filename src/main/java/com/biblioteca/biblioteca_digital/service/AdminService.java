package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import com.biblioteca.biblioteca_digital.dto.DashboardResponse;
import com.biblioteca.biblioteca_digital.dto.UsuarioResponse;
import com.biblioteca.biblioteca_digital.enums.Role;

public interface AdminService {
	List<UsuarioResponse> listarUsuarios();

    void cambiarRol(Integer id, Role role);

    void cambiarEstado(Integer id, String estado);
    
    DashboardResponse obtenerMetricas();


}
