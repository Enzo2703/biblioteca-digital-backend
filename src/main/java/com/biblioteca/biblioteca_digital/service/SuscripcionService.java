package com.biblioteca.biblioteca_digital.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.SuscripcionResponse;
import com.biblioteca.biblioteca_digital.dto.UsuarioResponse;
import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.mapper.SuscripcionMapper;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.SuscripcionRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuscripcionService {
	
	private final SuscripcionMapper suscripcionMapper;
    private final SuscripcionRepository suscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    
    //Activar suscripcion
    public SuscripcionResponse activarSuscripcion(Long usuarioId) {

        // 1️⃣ Verificar si ya tiene activa
        Optional<Suscripcion> activa =
                suscripcionRepository.findByUsuarioIdAndEstado(
                        usuarioId, EstadoSuscripcion.ACTIVA);

        if (activa.isPresent()
                && activa.get().getFechaFin().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Ya tienes una suscripción activa.");
        }

        // 2️⃣ Crear nueva
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Suscripcion nueva = new Suscripcion();
        nueva.setUsuario(usuario);
        nueva.setFechaInicio(LocalDateTime.now());
        nueva.setFechaFin(LocalDateTime.now().plusSeconds(30));
        nueva.setEstado(EstadoSuscripcion.ACTIVA);

        Suscripcion guardada = suscripcionRepository.save(nueva);
        return suscripcionMapper.toResponse(guardada);
    }
    
    //Cancelar suscripcion
    
    public void cancelarSuscripcion(Long usuarioId) {

        Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioIdAndEstado(usuarioId, EstadoSuscripcion.ACTIVA)
                .orElseThrow(() -> new RuntimeException("No tienes suscripción activa"));

        suscripcion.setEstado(EstadoSuscripcion.CANCELADA);

        suscripcionRepository.save(suscripcion);
    }
    
    //Renovar Suscripcion
    public Suscripcion renovarSuscripcion(Long usuarioId) {

        Suscripcion suscripcion = suscripcionRepository
                .findTopByUsuarioIdOrderByFechaFinDesc(usuarioId)
                .orElseThrow(() -> new RuntimeException("No existe historial de suscripción"));

        if (suscripcion.getEstado() == EstadoSuscripcion.ACTIVA
                && suscripcion.getFechaFin().isAfter(LocalDateTime.now())) {

            suscripcion.setFechaFin(suscripcion.getFechaFin().plusMonths(1));

        } else {
            suscripcion.setFechaInicio(LocalDateTime.now());
            suscripcion.setFechaFin(LocalDateTime.now().plusMonths(1));
            suscripcion.setEstado(EstadoSuscripcion.ACTIVA);
        }

        return suscripcionRepository.save(suscripcion);
    }
    
    //Validar Suscripcion Activa
    public boolean usuarioTieneSuscripcionActiva(Long usuarioId) {

        Optional<Suscripcion> suscripcionOpt =
                suscripcionRepository.findByUsuarioIdAndEstado(
                        usuarioId, EstadoSuscripcion.ACTIVA);

        if (suscripcionOpt.isEmpty()) return false;

        Suscripcion suscripcion = suscripcionOpt.get();

        if (suscripcion.getFechaFin().isBefore(LocalDateTime.now())) {
            suscripcion.setEstado(EstadoSuscripcion.VENCIDA);
            suscripcionRepository.save(suscripcion);
            return false;
        }

        return true;
    }
    
    public SuscripcionResponse obtenerUltimaSuscripcion(Long usuarioId) {
    	Suscripcion suscripcion = suscripcionRepository
    	        .findTopByUsuarioIdOrderByFechaFinDesc(usuarioId)
    	        .orElseThrow(() -> new RuntimeException("No tienes suscripciones"));
    	// 🔥 Validar vencimiento aquí
        if (suscripcion.getEstado() == EstadoSuscripcion.ACTIVA &&
            suscripcion.getFechaFin().isBefore(LocalDateTime.now())) {

            suscripcion.setEstado(EstadoSuscripcion.VENCIDA);
            suscripcionRepository.save(suscripcion);
        }

    	return suscripcionMapper.toResponse(suscripcion);
    }
}