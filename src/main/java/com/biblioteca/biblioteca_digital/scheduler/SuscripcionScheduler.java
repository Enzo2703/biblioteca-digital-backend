package com.biblioteca.biblioteca_digital.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.repository.SuscripcionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SuscripcionScheduler {
	
	private final SuscripcionRepository suscripcionRepository;
	
	@Transactional
	@Scheduled(cron = "0 0 0 * * ?", zone = "America/Lima") // cada día a medianoche
    public void actualizarSuscripcionesVencidas() {

        List<Suscripcion> vencidas =
                suscripcionRepository.findByEstadoAndFechaFinBefore(
                        EstadoSuscripcion.ACTIVA,
                        LocalDateTime.now()
                );

        for (Suscripcion s : vencidas) {
            s.setEstado(EstadoSuscripcion.VENCIDA);
        }

        suscripcionRepository.saveAll(vencidas);
    }


}
