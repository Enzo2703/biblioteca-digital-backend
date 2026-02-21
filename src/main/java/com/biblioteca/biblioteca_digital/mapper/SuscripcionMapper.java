package com.biblioteca.biblioteca_digital.mapper;

import org.springframework.stereotype.Component;

import com.biblioteca.biblioteca_digital.dto.SuscripcionResponse;
import com.biblioteca.biblioteca_digital.dto.UsuarioResponse;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.model.Usuario;

@Component
public class SuscripcionMapper {

    public SuscripcionResponse toResponse(Suscripcion suscripcion) {

        Usuario usuario = suscripcion.getUsuario();

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getEstado()
        );

        return new SuscripcionResponse(
                suscripcion.getId(),
                suscripcion.getFechaInicio(),
                suscripcion.getFechaFin(),
                suscripcion.getEstado(),
                usuarioResponse
        );
    }
}
