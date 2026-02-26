package com.biblioteca.biblioteca_digital.serviceImplement;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.exception.SuscripcionNoActivaException;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.SuscripcionRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.SuscripcionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuscripcionServiceImpl  implements SuscripcionService{
	
	private final SuscripcionRepository suscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private static final int DURACION_DIAS = 30;
    
    
	@Override
	public void validarSuscripcionActiva(Integer usuarioId) {
		Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() ->
                        new SuscripcionNoActivaException("No tienes suscripción activa. Suscríbete para acceder a libros premium.")
                );

        if (suscripcion.getEstado() != EstadoSuscripcion.ACTIVA
                || suscripcion.getFechaFin().isBefore(LocalDateTime.now())) {

            suscripcion.setEstado(EstadoSuscripcion.VENCIDA);
            suscripcionRepository.save(suscripcion);

            throw new SuscripcionNoActivaException("Tu suscripción está vencida. Reactívala para continuar.");
        }
		
	}
	@Override
	public void crearOReactivarSuscripcion(Integer usuarioId) {
		
		Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuarioId)
                .orElse(null);

        LocalDateTime ahora = LocalDateTime.now();

        if (suscripcion == null) {
            suscripcion = new Suscripcion();
            suscripcion.setUsuario(usuario);
        }

        suscripcion.setFechaInicio(ahora);
        suscripcion.setFechaFin(ahora.plusDays(DURACION_DIAS));
        suscripcion.setEstado(EstadoSuscripcion.ACTIVA);

        suscripcionRepository.save(suscripcion);
		
	}
	@Override
	public void cancelarSuscripcion(Integer usuarioId) {
		Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        suscripcion.setEstado(EstadoSuscripcion.CANCELADA);
        suscripcionRepository.save(suscripcion);
		
	}
	@Override
	public void renovarSuscripcion(Integer usuarioId) {
		Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        if (suscripcion.getEstado() == EstadoSuscripcion.ACTIVA) {
            suscripcion.setFechaFin(
                    suscripcion.getFechaFin().plusDays(DURACION_DIAS)
            );
        } else {
            crearOReactivarSuscripcion(usuarioId);
            return;
        }

        suscripcionRepository.save(suscripcion);
		
	}

}
